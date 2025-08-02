package com.solux.dorandoran.domain.entity

data class DiscussionEntity(
    val boardId: Long,
    val bookId: Long,
    val memberId: Long,
    val title: String,
    val content: String,
    val createdAt: String,
    // UI에 필요한 추가 필드들
    val bookTitle: String,
    val author: String,
    val imageUrl: String,
    val user: UserEntity? = null
)