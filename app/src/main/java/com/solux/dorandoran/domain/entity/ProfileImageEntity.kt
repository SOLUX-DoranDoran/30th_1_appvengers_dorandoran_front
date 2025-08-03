package com.solux.dorandoran.domain.entity

data class ProfileImageEntity(
    val fileName: String,
    val imageUrl: String
)

sealed class UploadResult {
    data class Success(val profileImage: ProfileImageEntity) : UploadResult()
    data class Error(val message: String) : UploadResult()
    object Loading : UploadResult()
}