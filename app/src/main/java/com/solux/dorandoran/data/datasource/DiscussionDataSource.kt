package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto

interface DiscussionDataSource {
    suspend fun getRecentDiscussions(): List<ResponseGetDiscussionDto>
}