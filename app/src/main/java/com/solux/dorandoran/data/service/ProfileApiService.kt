package com.solux.dorandoran.data.remote.api

import com.solux.dorandoran.data.dto.response.StorageImgResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ProfileApiService {

    @Multipart
    @POST("/me/profile_image")
    suspend fun uploadProfileImage(
        @Part image: MultipartBody.Part
    ): Response<StorageImgResponseDto>
}