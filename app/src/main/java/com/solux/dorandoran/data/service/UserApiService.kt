package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetUserDto
import retrofit2.http.*

interface UserApiService {

    @GET("/api/users/me")
    suspend fun getUserInfo(): ResponseGetUserDto

    @PUT("/api/users/me")
    suspend fun updateUserProfile(
        @Field("nickname") nickname: String,
        @Field("profileImage") profileImage: String?
    ): ResponseGetUserDto
}