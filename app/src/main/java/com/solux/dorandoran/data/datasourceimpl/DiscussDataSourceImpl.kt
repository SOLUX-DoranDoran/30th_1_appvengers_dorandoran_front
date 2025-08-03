package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.DiscussDataSource
import com.solux.dorandoran.data.dto.request.RequestCreateDiscussionDto
import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussionDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionsDto
import com.solux.dorandoran.data.service.DiscussApiService
import javax.inject.Inject

class DiscussDataSourceImpl @Inject constructor(
    private val discussApiService: DiscussApiService
) : DiscussDataSource {

    override suspend fun getDiscussions(
        token: String,
        page: Int,
        size: Int
    ): ResponseGetDiscussionsDto {
        return discussApiService.getDiscussions(
            authorization = "Bearer $token",
            page = page,
            size = size
        )
    }

    override suspend fun createDiscussion(
        token: String,
        bookId: String,
        title: String,
        content: String
    ): ResponseCreateDiscussionDto {
        return discussApiService.createDiscussion(
            authorization = "Bearer $token",
            request = RequestCreateDiscussionDto(
                bookId = bookId,
                title = title,
                content = content
            )
        )
    }

    override suspend fun getDiscussionDetail(
        token: String,
        discussionId: Int
    ): ResponseGetDiscussionItemDto {
        return discussApiService.getDiscussionDetail(
            authorization = "Bearer $token",
            discussionId = discussionId
        )
    }
}