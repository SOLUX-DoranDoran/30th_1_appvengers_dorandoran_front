package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetBookDto
import com.solux.dorandoran.domain.entity.BookEntity

fun ResponseGetBookDto.toBookEntity() = BookEntity(
    id, title, author, publisher, publisherDate, coverImageUrl
)