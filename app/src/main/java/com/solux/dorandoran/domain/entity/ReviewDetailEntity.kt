package com.solux.dorandoran.domain.entity

data class ReviewDetailEntity(
    val id: Long,
    val bookTitle: String,
    val coverImageUrl: String,
    val content: String,
    val rating: Int,
    val createdAt: String,
    val nickname: String,
    val profileImage: String?,
    // UI에서만 사용하는 필드들
    val isLiked: Boolean = false,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val comments: List<ReviewCommentEntity> = emptyList(),
    val isCommentsVisible: Boolean = false
)