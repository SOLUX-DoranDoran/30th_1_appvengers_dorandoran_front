package com.solux.dorandoran.domain.entity

data class DiscussCommentEntity (
    val id: Int,
    val memberNickname: String,
    val content: String,
    val createdAt: String,
    val parentId: Int?
)