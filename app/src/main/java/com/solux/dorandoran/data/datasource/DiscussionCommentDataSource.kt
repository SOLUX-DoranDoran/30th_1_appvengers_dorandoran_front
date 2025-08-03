package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussCommentsDto

interface DiscussCommentDataSource {
    // 토론 댓글 목록 조회
    suspend fun getDiscussComments(
        boardId: Int,
        page: Int,
        size: Int
    ): ResponseGetDiscussCommentsDto

    // 토론 댓글/대댓글 작성
    suspend fun createDiscussComment(
        boardId: Int,
        content: String,
        parentId: Int?
    ): ResponseCreateDiscussCommentDto
}