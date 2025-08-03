package com.solux.dorandoran.data.datasource.impl

import android.content.SharedPreferences
import com.solux.dorandoran.data.datasource.ProfileDataSource
import com.solux.dorandoran.data.datasource.ProfileLocalDataSource
import com.solux.dorandoran.data.dto.response.StorageImgResponseDto
import com.solux.dorandoran.data.remote.api.ProfileApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val apiService: ProfileApiService
) : ProfileDataSource {

    override suspend fun uploadProfileImage(imageFile: File): Response<StorageImgResponseDto> {
        val imagePart = prepareFilePart("image", imageFile)
        return apiService.uploadProfileImage(imagePart)
    }

    private fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
}

class ProfileLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ProfileLocalDataSource {

    companion object {
        private const val KEY_PROFILE_IMAGE_URL = "profile_image_url"
    }

    override suspend fun saveProfileImageUrl(url: String) {
        sharedPreferences.edit()
            .putString(KEY_PROFILE_IMAGE_URL, url)
            .apply()
    }

    override suspend fun getProfileImageUrl(): String? {
        return sharedPreferences.getString(KEY_PROFILE_IMAGE_URL, null)
    }

    override suspend fun clearProfileData() {
        sharedPreferences.edit()
            .remove(KEY_PROFILE_IMAGE_URL)
            .apply()
    }
}