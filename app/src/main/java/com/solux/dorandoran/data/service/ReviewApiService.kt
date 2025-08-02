package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.request.RequestCreateCommentDto
import com.solux.dorandoran.data.dto.request.RequestCreateReviewDto
import com.solux.dorandoran.data.dto.response.ResponseCreateReviewDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookReviewsDto
import com.solux.dorandoran.data.dto.response.ResponseGetRecentReviewDto
import com.solux.dorandoran.data.dto.response.ResponseGetReviewCommentsDto
import com.solux.dorandoran.data.dto.response.ResponseGetReviewDetailDto
import com.solux.dorandoran.data.dto.response.ResponsePostCreateCommentDto
import com.solux.dorandoran.data.dto.response.ResponseReviewLikeDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewApiService {

    @GET("/api/reviews")
    suspend fun getRecentReviews(
        @Query("sort") sort: String = "recent",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): List<ResponseGetRecentReviewDto>

    @GET("/api/reviews/recent")
    suspend fun getRecentReview(
        @Query("sort") sort: String = "recent"
    ): ResponseGetRecentReviewDto

    @GET("/api/books/{bookId}/reviews")
    suspend fun getBookReviews(
        @Path("bookId") bookId: Long,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ResponseGetBookReviewsDto

    @GET("/api/reviews/{reviewId}")
    suspend fun getReviewDetail(
        @Path("reviewId") reviewId: Long
    ): ResponseGetReviewDetailDto

    @POST("/api/reviews/{reviewId}/like")
    suspend fun addReviewLike(
        @Path("reviewId") reviewId: Long
    ): ResponseReviewLikeDto

    @DELETE("/api/reviews/{reviewId}/like")
    suspend fun removeReviewLike(
        @Path("reviewId") reviewId: Long
    ): ResponseReviewLikeDto

    @GET("/api/reviews/{reviewId}/comments")
    suspend fun getReviewComments(
        @Path("reviewId") reviewId: Long,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ResponseGetReviewCommentsDto

    @POST("/api/books/{bookId}/reviews")
    suspend fun createReview(
        @Path("bookId") bookId: Long,
        @Body request: RequestCreateReviewDto
    ): ResponseCreateReviewDto

    @POST("/api/reviews/{reviewId}/comments")
    suspend fun createReviewComment(
        @Path("reviewId") reviewId: Long,
        @Body request: RequestCreateCommentDto
    ): ResponsePostCreateCommentDto
}