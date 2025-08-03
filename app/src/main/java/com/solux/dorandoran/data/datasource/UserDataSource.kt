package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseGetUserDto

interface UserDataSource {
    suspend fun getUserInfo(token: String): ResponseGetUserDto
    suspend fun updateUserProfile(token: String, nickname: String, profileImage: String?): ResponseGetUserDto
}