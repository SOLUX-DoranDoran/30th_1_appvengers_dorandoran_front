package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.request.RequestCreateDiscussionDto
import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussionDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionsDto

interface DiscussDataSource {
    suspend fun getDiscussions(
        token: String,
        page: Int,
        size: Int
    ): ResponseGetDiscussionsDto

    suspend fun createDiscussion(
        token: String,
        bookId: String,
        title: String,
        content: String
    ): ResponseCreateDiscussionDto

    suspend fun getDiscussionDetail(
        token: String,
        discussionId: Int
    ): ResponseGetDiscussionItemDto
}