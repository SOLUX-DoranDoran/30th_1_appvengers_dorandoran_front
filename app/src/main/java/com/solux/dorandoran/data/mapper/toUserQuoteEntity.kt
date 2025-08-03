package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetUserQuoteDto
import com.solux.dorandoran.domain.entity.UserQuoteEntity

fun ResponseGetUserQuoteDto.toUserQuoteEntity() = UserQuoteEntity(
    id = id,
    bookName = bookName,
    content = content,
    createdAt = createdAt,
    likeCount = likeCount
)