package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetCommentItemDto
import com.solux.dorandoran.domain.entity.ReviewCommentEntity

fun ResponseGetCommentItemDto.toReviewCommentEntity() = ReviewCommentEntity(
    id = id,
    parentCommentNickname = parentCommentNickname,
    nickname = nickname,
    profileImage = profileImage,
    content = content,
    createdAt = createdAt
)