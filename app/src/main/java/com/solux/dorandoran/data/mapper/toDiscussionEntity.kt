package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto
import com.solux.dorandoran.domain.entity.DiscussionEntity
import com.solux.dorandoran.domain.entity.UserEntity

fun ResponseGetDiscussionDto.toDiscussionEntity() = DiscussionEntity(
    boardId = boardId,
    bookId = bookId,
    memberId = memberId,
    title = title,
    content = content,
    createdAt = createdAt,
    bookTitle = "",
    author = "",
    imageUrl = "",
    user = UserEntity( // TODO: UserRepository에서 가져오기
        id = memberId,
        email = "",
        nickname = "사용자$memberId",
        profileImage = null,
        createdAt = ""
    )
)