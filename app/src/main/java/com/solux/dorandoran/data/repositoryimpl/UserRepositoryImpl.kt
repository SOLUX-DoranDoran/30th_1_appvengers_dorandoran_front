package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import com.solux.dorandoran.data.datasource.UserDataSource
import com.solux.dorandoran.data.mapper.toUserEntity
import com.solux.dorandoran.domain.entity.UserEntity
import com.solux.dorandoran.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    @ApplicationContext private val context: Context
) : UserRepository {

    private suspend fun getAccessToken(): String {
        // 수정: 실제 토큰 관리 로직으로 대체 필요
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
    }

    override suspend fun getUserInfo(): Result<UserEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = userDataSource.getUserInfo(token)
            response.toUserEntity()
        }
    }

    override suspend fun updateUserProfile(
        nickname: String,
        profileImage: String?
    ): Result<UserEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = userDataSource.updateUserProfile(token, nickname, profileImage)
            response.toUserEntity()
        }
    }
}
