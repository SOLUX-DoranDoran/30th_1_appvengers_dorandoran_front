package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.DiscussCommentEntity

interface DiscussCommentRepository {
    suspend fun getDiscussComments(
        boardId: Int,
        page: Int = 0,
        size: Int = 10
    ): Result<List<DiscussCommentEntity>>

    suspend fun createDiscussComment(
        boardId: Int,
        content: String,
        parentId: Int? = null
    ): Result<DiscussCommentEntity>
}