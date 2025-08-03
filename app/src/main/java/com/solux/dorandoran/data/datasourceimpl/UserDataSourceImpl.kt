package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.UserDataSource
import com.solux.dorandoran.data.dto.response.ResponseGetUserDto
import com.solux.dorandoran.data.service.UserApiService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserDataSource {

    override suspend fun getUserInfo(token: String): ResponseGetUserDto {
        return userApiService.getUserInfo("Bearer $token")
    }

    override suspend fun updateUserProfile(
        token: String,
        nickname: String,
        profileImage: String?
    ): ResponseGetUserDto {
        return userApiService.updateUserProfile(
            authorization = "Bearer $token",
            nickname = nickname,
            profileImage = profileImage
        )
    }
}