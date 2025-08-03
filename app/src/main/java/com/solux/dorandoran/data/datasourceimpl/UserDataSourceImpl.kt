package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.UserDataSource
import com.solux.dorandoran.data.dto.response.ResponseGetUserDto
import com.solux.dorandoran.data.service.UserApiService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserDataSource {

    // 수정: AuthInterceptor에서 자동으로 토큰 추가하므로 토큰 파라미터 제거
    override suspend fun getUserInfo(): ResponseGetUserDto {
        return userApiService.getUserInfo()
    }

    override suspend fun updateUserProfile(
        nickname: String,
        profileImage: String?
    ): ResponseGetUserDto {
        return userApiService.updateUserProfile(
            nickname = nickname,
            profileImage = profileImage
        )
    }
}