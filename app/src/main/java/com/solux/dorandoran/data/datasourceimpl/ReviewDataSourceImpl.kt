package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.ReviewDataSource
import com.solux.dorandoran.data.dto.request.RequestCreateCommentDto
import com.solux.dorandoran.data.dto.request.RequestCreateReviewDto
import com.solux.dorandoran.data.dto.response.ResponseCreateReviewDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookReviewsDto
import com.solux.dorandoran.data.dto.response.ResponseGetRecentReviewDto
import com.solux.dorandoran.data.dto.response.ResponseGetReviewCommentsDto
import com.solux.dorandoran.data.dto.response.ResponseGetReviewDetailDto
import com.solux.dorandoran.data.dto.response.ResponsePostCreateCommentDto
import com.solux.dorandoran.data.dto.response.ResponseReviewLikeDto
import com.solux.dorandoran.data.service.ReviewApiService
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewApiService: ReviewApiService
) : ReviewDataSource {

    override suspend fun getRecentReviews(
        token: String,
        sort: String,
        page: Int,
        size: Int
    ): List<ResponseGetRecentReviewDto> {
        return reviewApiService.getRecentReviews(
            authorization = "Bearer $token",
            sort = sort,
            page = page,
            size = size
        )
    }

    override suspend fun getRecentReview(
        token: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseGetRecentReviewDto {
        return reviewApiService.getRecentReview(
            authorization = "Bearer $token",
            sort = sort
        )
    }

    override suspend fun getBookReviews(
        token: String,
        bookId: Long,
        page: Int,
        size: Int
    ): ResponseGetBookReviewsDto {
        return reviewApiService.getBookReviews(
            authorization = "Bearer $token",
            bookId = bookId,
            page = page,
            size = size
        )
    }

    override suspend fun getReviewDetail(
        token: String,
        reviewId: Long
    ): ResponseGetReviewDetailDto {
        return reviewApiService.getReviewDetail(
            authorization = "Bearer $token",
            reviewId = reviewId
        )
    }

    override suspend fun addReviewLike(
        token: String,
        reviewId: Long
    ): ResponseReviewLikeDto {
        return reviewApiService.addReviewLike(
            authorization = "Bearer $token",
            reviewId = reviewId
        )
    }

    override suspend fun removeReviewLike(
        token: String,
        reviewId: Long
    ): ResponseReviewLikeDto {
        return reviewApiService.removeReviewLike(
            authorization = "Bearer $token",
            reviewId = reviewId
        )
    }

    override suspend fun getReviewComments(
        token: String,
        reviewId: Long,
        page: Int,
        size: Int
    ): ResponseGetReviewCommentsDto {
        return reviewApiService.getReviewComments(
            authorization = "Bearer $token",
            reviewId = reviewId,
            page = page,
            size = size
        )
    }

    override suspend fun createReview(
        token: String,
        bookId: Long,
        request: RequestCreateReviewDto
    ): ResponseCreateReviewDto {
        return reviewApiService.createReview(
            authorization = "Bearer $token",
            bookId = bookId,
            request = request
        )
    }

    override suspend fun createReviewComment(
        token: String,
        reviewId: Long,
        request: RequestCreateCommentDto
    ): ResponsePostCreateCommentDto {
        return reviewApiService.createReviewComment("Bearer $token", reviewId, request)
    }
}