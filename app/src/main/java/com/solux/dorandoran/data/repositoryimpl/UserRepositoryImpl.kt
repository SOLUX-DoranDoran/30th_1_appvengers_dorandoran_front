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

    override suspend fun getUserInfo(): Result<UserEntity> {
        return runCatching {
            val response = userDataSource.getUserInfo()
            response.toUserEntity()
        }
    }

    override suspend fun updateUserProfile(
        nickname: String,
        profileImage: String?
    ): Result<UserEntity> {
        return runCatching {
            val response = userDataSource.updateUserProfile(nickname, profileImage)
            response.toUserEntity()
        }
    }
}
