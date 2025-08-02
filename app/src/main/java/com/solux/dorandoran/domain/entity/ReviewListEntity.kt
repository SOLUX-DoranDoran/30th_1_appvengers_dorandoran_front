package com.solux.dorandoran.domain.entity

data class ReviewListEntity(
    val reviewId: Long,
    val bookId: Long,
    val bookTitle: String,
    val coverImageUrl: String,
    val content: String,
    val rating: Int,
    val createdAt: String,
    val nickname: String,
    val profileImage: String?
)