package com.solux.dorandoran.domain.entity

data class BookReviewsResponseEntity(
    val reviews: List<ReviewListEntity>,
    val totalPages: Int,
    val currentPage: Int,
    val totalReviews: Int
)