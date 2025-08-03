package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.DiscussPageEntity

interface DiscussRepository {
    suspend fun getDiscussions(
        page: Int,
        size: Int
    ): Result<List<DiscussPageEntity>>

    suspend fun createDiscussion(
        bookId: String,
        title: String,
        content: String
    ): Result<Int>

    suspend fun getDiscussionDetail(
        discussionId: Int
    ): Result<DiscussPageEntity>
}