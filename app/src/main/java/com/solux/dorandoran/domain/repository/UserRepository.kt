package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.UserEntity

interface UserRepository {
    suspend fun getUserInfo(): Result<UserEntity>
    suspend fun updateUserProfile(nickname: String, profileImage: String?): Result<UserEntity>
}