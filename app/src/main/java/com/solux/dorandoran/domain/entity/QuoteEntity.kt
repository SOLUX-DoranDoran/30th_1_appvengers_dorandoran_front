package com.solux.dorandoran.domain.entity

data class QuoteEntity (
    val id: Long,
    val bookId: Long,
    val bookTitle: String,
    val coverImageUrl: String,
    val content: String,
    val createdAt: String,
    val nickname: String,
    val profileImage: String?,
    // 좋아요 관련 필드 추가
    val isLiked: Boolean = false,
    val likeCount: Int = 0
)