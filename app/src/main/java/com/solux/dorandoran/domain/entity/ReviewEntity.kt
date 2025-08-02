package com.solux.dorandoran.domain.entity

data class ReviewEntity(
    val id: Long,
    val bookTitle: String,
    val coverImageUrl : String,
    val content: String,
    val rating: Int,
    val createdAt: String,
    val nickname: String,
    val profileImage: String,
    val bookId: Long,
    val userId: Long,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isLiked: Boolean = false
)