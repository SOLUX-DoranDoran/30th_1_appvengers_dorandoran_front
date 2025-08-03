package com.solux.dorandoran.domain.entity

data class BoardEntity(
    val id: Int, // 수정: boardId가 아닌 id
    val bookId: Int,
    val memberId: Int,
    val title: String,
    val content: String,
    val createdAt: String
)