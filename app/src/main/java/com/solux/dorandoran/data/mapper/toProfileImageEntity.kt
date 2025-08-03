package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.StorageImgResponseDto
import com.solux.dorandoran.domain.entity.ProfileImageEntity

fun StorageImgResponseDto.toProfileImageEntity(): ProfileImageEntity {
    return ProfileImageEntity(
        fileName = this.fileName ?: "",
        imageUrl = this.profileImageUrl ?: ""
    )
}

fun StorageImgResponseDto.toDomain(): ProfileImageEntity? {
    return if (this.success && this.fileName != null && this.profileImageUrl != null) {
        ProfileImageEntity(
            fileName = this.fileName,
            imageUrl = this.profileImageUrl
        )
    } else {
        null
    }
}