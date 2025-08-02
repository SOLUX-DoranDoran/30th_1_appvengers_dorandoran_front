package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetQuoteDto
import com.solux.dorandoran.domain.entity.QuoteEntity

fun ResponseGetQuoteDto.toQuoteEntity() = QuoteEntity(
    id = id,
    bookId = bookId,
    bookTitle = bookTitle,
    coverImageUrl = coverImageUrl,
    content = content,
    createdAt = createdAt,
    nickname = nickname,
    profileImage = profileImage,
    isLiked = false,
    likeCount = 0
)