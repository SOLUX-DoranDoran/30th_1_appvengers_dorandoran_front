package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetBookReviewItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetRecentReviewDto
import com.solux.dorandoran.domain.entity.ReviewListEntity

fun ResponseGetRecentReviewDto.toReviewListEntity() = ReviewListEntity(
    reviewId = reviewId,
    bookId = bookId,
    bookTitle = bookTitle,
    coverImageUrl = coverImageUrl,
    content = content,
    rating = rating,
    createdAt = createdAt,
    nickname = nickname,
    profileImage = profileImage
)

fun ResponseGetBookReviewItemDto.toReviewListEntity() = ReviewListEntity(
    reviewId = reviewId,
    bookId = bookId,
    bookTitle = bookTitle,
    coverImageUrl = coverImageUrl,
    content = content,
    rating = rating,
    createdAt = createdAt,
    nickname = nickname,
    profileImage = profileImage
)