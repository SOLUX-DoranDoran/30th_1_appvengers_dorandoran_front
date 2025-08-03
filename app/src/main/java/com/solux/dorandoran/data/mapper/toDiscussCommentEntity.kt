package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussCommentItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussCommentsDto
import com.solux.dorandoran.domain.entity.DiscussCommentEntity

fun ResponseGetDiscussCommentItemDto.toDiscussCommentEntity() = DiscussCommentEntity(
    id = id,
    memberNickname = memberNickname,
    content = content,
    createdAt = createdAt,
    parentId = parentId
)

fun ResponseGetDiscussCommentsDto.toDiscussCommentEntityList(): List<DiscussCommentEntity> {
    return content.map { it.toDiscussCommentEntity() }
}

fun ResponseCreateDiscussCommentDto.toDiscussCommentEntity() = DiscussCommentEntity(
    id = id,
    memberNickname = memberNickname,
    content = content,
    createdAt = createdAt,
    parentId = parentId
)