package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionDto
import retrofit2.http.GET
import retrofit2.http.Header

interface DiscussionApiService {
    @GET("/api/boards/recent")
    suspend fun getRecentDiscussions(
        @Header("Authorization") authorization: String
    ): List<ResponseGetDiscussionDto>
}