package com.solux.dorandoran.domain.entity

data class PagedBoardsEntity(
    val content: List<BoardEntity>,
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val first: Boolean,
    val last: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)