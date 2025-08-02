package com.solux.dorandoran.domain.entity

data class EmotionShareEntity(
    val id: Long,
    val bookId: Long,
    val bookTitle: String,
    val coverImageUrl: String,
    val content: String,
    val createdAt: String,
    val nickname: String,
    val profileImage: String
)