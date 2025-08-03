package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseGetUserDto

interface UserDataSource {
    suspend fun getUserInfo(): ResponseGetUserDto
    suspend fun updateUserProfile(nickname: String, profileImage: String?): ResponseGetUserDto
}