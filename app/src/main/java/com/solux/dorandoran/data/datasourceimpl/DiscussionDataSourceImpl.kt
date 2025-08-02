package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.DiscussionDataSource
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto
import com.solux.dorandoran.data.service.DiscussionApiService
import javax.inject.Inject

class DiscussionDataSourceImpl @Inject constructor(
    private val discussionApiService: DiscussionApiService
) : DiscussionDataSource {
    override suspend fun getRecentDiscussions(
        token: String
    ): List<ResponseGetDiscussionDto> {
        return discussionApiService.getRecentDiscussions(
            authorization = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
        )
    }
}