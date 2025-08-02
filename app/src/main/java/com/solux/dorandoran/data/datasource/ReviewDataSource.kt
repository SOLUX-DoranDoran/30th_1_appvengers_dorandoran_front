package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.request.RequestCreateCommentDto
import com.solux.dorandoran.data.dto.request.RequestCreateReviewDto
import com.solux.dorandoran.data.dto.response.ResponseCreateReviewDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookReviewsDto
import com.solux.dorandoran.data.dto.response.ResponseGetRecentReviewDto
import com.solux.dorandoran.data.dto.response.ResponseGetReviewCommentsDto
import com.solux.dorandoran.data.dto.response.ResponseGetReviewDetailDto
import com.solux.dorandoran.data.dto.response.ResponsePostCreateCommentDto
import com.solux.dorandoran.data.dto.response.ResponseReviewLikeDto

interface ReviewDataSource {

    suspend fun getRecentReviews(
        sort: String = "recent",
        page: Int = 1,
        size: Int = 10
    ): List<ResponseGetRecentReviewDto>

    suspend fun getRecentReview(
        sort: String = "recent",
        page: Int = 1,
        size: Int = 1
    ): ResponseGetRecentReviewDto

    suspend fun getBookReviews(
        bookId: Long,
        page: Int = 1,
        size: Int = 10
    ): ResponseGetBookReviewsDto

    suspend fun getReviewDetail(
        reviewId: Long
    ): ResponseGetReviewDetailDto

    suspend fun addReviewLike(
        reviewId: Long
    ): ResponseReviewLikeDto

    suspend fun removeReviewLike(
        reviewId: Long
    ): ResponseReviewLikeDto

    suspend fun getReviewComments(
        reviewId: Long,
        page: Int = 1,
        size: Int = 10
    ): ResponseGetReviewCommentsDto

    suspend fun createReview(
        bookId: Long,
        request: RequestCreateReviewDto
    ): ResponseCreateReviewDto

    suspend fun createReviewComment(
        reviewId: Long,
        request: RequestCreateCommentDto
    ): ResponsePostCreateCommentDto
}