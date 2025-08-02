package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetReviewDetailDto
import com.solux.dorandoran.domain.entity.ReviewDetailEntity

fun ResponseGetReviewDetailDto.toReviewDetailEntity() = ReviewDetailEntity(
    id = id,
    bookTitle = bookTitle,
    coverImageUrl = coverImageUrl,
    content = content,
    rating = rating,
    createdAt = createdAt,
    nickname = nickname,
    profileImage = profileImage,
    // UI 상태는 기본값
    isLiked = false,
    likeCount = 0,
    commentCount = 0,
    comments = emptyList(),
    isCommentsVisible = false
)