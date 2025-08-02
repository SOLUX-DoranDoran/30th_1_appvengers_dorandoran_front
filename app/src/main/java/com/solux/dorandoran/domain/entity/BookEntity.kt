package com.solux.dorandoran.domain.entity

data class BookEntity(
    val id: Long,
    val title: String,
    val author: String,
    val publisher: String,
    val publisherDate: String,
    val coverImageUrl: String,
)