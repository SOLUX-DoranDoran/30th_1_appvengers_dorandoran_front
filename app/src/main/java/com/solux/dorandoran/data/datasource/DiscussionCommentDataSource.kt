package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussCommentsDto

interface DiscussCommentDataSource {
    suspend fun getDiscussComments(
        token: String,
        boardId: Int,
        page: Int,
        size: Int
    ): ResponseGetDiscussCommentsDto

    suspend fun createDiscussComment(
        token: String,
        boardId: Int,
        content: String,
        parentId: Int?
    ): ResponseCreateDiscussCommentDto
}