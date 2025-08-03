package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.request.RequestCreateDiscussionDto
import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussionDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussionsDto
import retrofit2.http.*

interface DiscussApiService {

    @GET("/api/discussions")
    suspend fun getDiscussions(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: String = "createdAt,desc"
    ): ResponseGetDiscussionsDto

    @POST("/api/discussions")
    suspend fun createDiscussion(
        @Header("Authorization") authorization: String,
        @Body request: RequestCreateDiscussionDto
    ): ResponseCreateDiscussionDto

    @GET("/api/discussions/{discussionId}")
    suspend fun getDiscussionDetail(
        @Header("Authorization") authorization: String,
        @Path("discussionId") discussionId: Int
    ): ResponseGetDiscussionItemDto
}