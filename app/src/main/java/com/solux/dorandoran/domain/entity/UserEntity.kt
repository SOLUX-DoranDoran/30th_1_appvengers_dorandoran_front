package com.solux.dorandoran.domain.entity

data class UserEntity(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImage: String?,
    val createdAt: String
)