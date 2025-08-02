package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetRecommendedBookDto
import com.solux.dorandoran.domain.entity.RecommendedBookEntity

fun ResponseGetRecommendedBookDto.toRecommendedBookEntity() = RecommendedBookEntity(
    id = id,
    title = title,
    author = author,
    publisher = publisher,
    publisherDate = publisherDate,
    coverImageUrl = coverImageUrl
)