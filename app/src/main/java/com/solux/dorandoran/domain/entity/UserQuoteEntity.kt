package com.solux.dorandoran.domain.entity

data class UserQuoteEntity(
    val id: Long,
    val bookName: String,
    val content: String,
    val createdAt: String,
    val likeCount: Int,
)