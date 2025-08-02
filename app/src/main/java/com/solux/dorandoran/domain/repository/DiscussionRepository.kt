package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.DiscussionEntity

interface DiscussionRepository {
    suspend fun getRecentDiscussions(): Result<List<DiscussionEntity>>

    suspend fun getRecentDiscussion(): Result<DiscussionEntity?>
}