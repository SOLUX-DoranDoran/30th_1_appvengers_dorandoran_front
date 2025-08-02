package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.DiscussionDataSource
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto
import com.solux.dorandoran.data.service.DiscussionApiService
import javax.inject.Inject

class DiscussionDataSourceImpl @Inject constructor(
    private val discussionApiService: DiscussionApiService
) : DiscussionDataSource {
    override suspend fun getRecentDiscussions(): List<ResponseGetDiscussionDto> {
        return discussionApiService.getRecentDiscussions()
    }
}