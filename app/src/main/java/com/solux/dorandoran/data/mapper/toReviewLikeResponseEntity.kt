package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseReviewLikeDto
import com.solux.dorandoran.domain.entity.ReviewLikeResponseEntity

fun ResponseReviewLikeDto.toReviewLikeResponseEntity() = ReviewLikeResponseEntity(
    success = success,
    message = message,
    likeCount = likeCount
)