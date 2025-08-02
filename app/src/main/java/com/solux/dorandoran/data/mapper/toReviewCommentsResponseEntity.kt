package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetReviewCommentsDto
import com.solux.dorandoran.domain.entity.ReviewCommentsResponseEntity

fun ResponseGetReviewCommentsDto.toReviewCommentsResponseEntity() = ReviewCommentsResponseEntity(
    totalComments = totalComments,
    comments = comments.map { it.toReviewCommentEntity() },
    currentPage = currentPage,
    totalPages = totalPages
)