package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.StorageImgResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File

interface ProfileDataSource {
    suspend fun uploadProfileImage(imageFile: File): Response<StorageImgResponseDto>
}

interface ProfileLocalDataSource {
    suspend fun saveProfileImageUrl(url: String)
    suspend fun getProfileImageUrl(): String?
    suspend fun clearProfileData()
}