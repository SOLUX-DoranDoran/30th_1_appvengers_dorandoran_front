package com.solux.dorandoran.domain.entity

data class QuoteLikeResponseEntity (
    val success: Boolean,
    val message: String,
    val likeCount: Int
)