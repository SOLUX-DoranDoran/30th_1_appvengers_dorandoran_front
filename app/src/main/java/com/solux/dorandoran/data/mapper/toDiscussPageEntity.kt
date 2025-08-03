package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionsDto
import com.solux.dorandoran.domain.entity.DiscussPageEntity

fun ResponseGetDiscussionItemDto.toDiscussPageEntity() = DiscussPageEntity(
    boardId = boardId,
    bookId = bookId,
    memberId = memberId,
    bookTitle = bookTitle,
    content = content,
    createdAt = createdAt
)

fun ResponseGetDiscussionsDto.toDiscussPageEntityList(): List<DiscussPageEntity> {
    return content.map { it.toDiscussPageEntity() }
}