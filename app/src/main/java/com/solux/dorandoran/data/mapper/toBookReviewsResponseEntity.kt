package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetBookReviewsDto
import com.solux.dorandoran.domain.entity.BookReviewsResponseEntity


fun ResponseGetBookReviewsDto.toBookReviewsResponseEntity() = BookReviewsResponseEntity(
    reviews = reviews.map { it.toReviewListEntity() },
    totalPages = totalPages,
    currentPage = currentPage,
    totalReviews = totalReviews
)