package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.ProfileImageEntity
import com.solux.dorandoran.domain.entity.UploadResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {

    suspend fun uploadProfileImage(imageFile: File): Flow<UploadResult>

    suspend fun getLocalProfileImageUrl(): String?

}

data class UserProfile(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?
)