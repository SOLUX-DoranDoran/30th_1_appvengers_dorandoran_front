package com.solux.dorandoran.domain.entity

data class ReviewCreateRequestEntity(
    val content: String,
    val rating: Int
)