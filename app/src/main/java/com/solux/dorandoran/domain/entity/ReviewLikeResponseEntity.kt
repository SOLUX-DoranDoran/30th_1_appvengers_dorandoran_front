package com.solux.dorandoran.domain.entity

data class ReviewLikeResponseEntity(
    val success: Boolean,
    val message: String,
    val likeCount: Int
)