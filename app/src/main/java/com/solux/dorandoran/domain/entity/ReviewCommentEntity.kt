package com.solux.dorandoran.domain.entity

data class ReviewCommentEntity(
    val id: Long,
    val parentCommentNickname: String,
    val nickname: String,
    val profileImage: String?,
    val content: String,
    val createdAt: String
)