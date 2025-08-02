package com.solux.dorandoran.domain.entity

data class ReviewCommentsResponseEntity(
    val totalComments: Int,
    val comments: List<ReviewCommentEntity>,
    val currentPage: Int,
    val totalPages: Int
)