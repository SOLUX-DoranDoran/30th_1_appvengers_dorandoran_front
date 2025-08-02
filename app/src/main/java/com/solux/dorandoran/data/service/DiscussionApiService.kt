package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto
import retrofit2.http.GET

interface DiscussionApiService {
    @GET("/api/boards/recent")
    suspend fun getRecentDiscussions(): List<ResponseGetDiscussionDto>
}