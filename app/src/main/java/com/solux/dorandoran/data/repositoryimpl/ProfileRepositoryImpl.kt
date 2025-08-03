package com.solux.dorandoran.data.repository

import com.solux.dorandoran.data.datasource.ProfileDataSource
import com.solux.dorandoran.data.datasource.ProfileLocalDataSource
import com.solux.dorandoran.domain.entity.UploadResult
import com.solux.dorandoran.domain.repository.ProfileRepository
import com.solux.dorandoran.data.dto.response.StorageImgErrorResponseDto
import com.solux.dorandoran.data.mapper.toProfileImageEntity
import com.solux.dorandoran.data.mapper.toDomain
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource,
    private val profileLocalDataSource: ProfileLocalDataSource,
    private val gson: Gson
) : ProfileRepository {

    override suspend fun uploadProfileImage(imageFile: File): Flow<UploadResult> = flow {
        try {
            emit(UploadResult.Loading)

            // 파일 검증
            if (!isValidImageFile(imageFile)) {
                emit(UploadResult.Error("지원하지 않는 파일 형식입니다. (jpg, jpeg, png만 허용)"))
                return@flow
            }

            // 파일 크기 검증 (5MB 제한)
            if (imageFile.length() > 5 * 1024 * 1024) {
                emit(UploadResult.Error("파일 크기가 너무 큽니다. 최대 5MB까지 업로드할 수 있습니다."))
                return@flow
            }

            val response = profileDataSource.uploadProfileImage(imageFile)

            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.success == true) {
                    val profileImage = responseBody.toDomain()
                    if (profileImage != null) {
                        // 로컬에 URL 저장
                        profileLocalDataSource.saveProfileImageUrl(profileImage.imageUrl)
                        emit(UploadResult.Success(profileImage))
                    } else {
                        emit(UploadResult.Error("서버 응답을 처리하는 중 오류가 발생했습니다."))
                    }
                } else {
                    emit(UploadResult.Error("업로드에 실패했습니다."))
                }
            } else {
                val errorMessage = parseErrorMessage(response.errorBody()?.string())
                emit(UploadResult.Error(errorMessage))
            }
        } catch (e: Exception) {
            emit(UploadResult.Error("네트워크 오류가 발생했습니다: ${e.message}"))
        }
    }

    override suspend fun getLocalProfileImageUrl(): String? {
        return profileLocalDataSource.getProfileImageUrl()
    }

    /**
     * 이미지 파일 유효성 검사
     */
    private fun isValidImageFile(file: File): Boolean {
        val allowedExtensions = listOf("jpg", "jpeg", "png")
        val fileExtension = file.extension.lowercase()
        return allowedExtensions.contains(fileExtension)
    }

    /**
     * 에러 메시지 파싱
     */
    private fun parseErrorMessage(errorBody: String?): String {
        return try {
            if (errorBody != null) {
                val errorResponse = gson.fromJson(errorBody, StorageImgErrorResponseDto::class.java)
                errorResponse.message
            } else {
                "알 수 없는 오류가 발생했습니다."
            }
        } catch (e: Exception) {
            "서버 오류가 발생했습니다."
        }
    }
}