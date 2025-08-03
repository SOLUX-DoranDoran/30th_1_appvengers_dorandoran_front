package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.request.RequestCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseCreateDiscussCommentDto
import com.solux.dorandoran.data.dto.response.ResponseGetDiscussCommentsDto
import retrofit2.http.*

interface DiscussCommentApiService {

    @GET("/api/boards/{boardId}/comments")
    suspend fun getDiscussComments(
        @Header("Authorization") authorization: String,
        @Path("boardId") boardId: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("sort") sort: String = "createdAt,asc"
    ): ResponseGetDiscussCommentsDto

    @POST("/api/boards/{boardId}/comments")
    suspend fun createDiscussComment(
        @Header("Authorization") authorization: String,
        @Path("boardId") boardId: Int,
        @Body request: RequestCreateDiscussCommentDto
    ): ResponseCreateDiscussCommentDto
}