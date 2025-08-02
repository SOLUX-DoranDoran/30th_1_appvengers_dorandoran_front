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
        sort: String,
        page: Int,
        size: Int
    ): List<ResponseGetRecentReviewDto> {
        return reviewApiService.getRecentReviews(
            sort = sort,
            page = page,
            size = size
        )
    }

    override suspend fun getRecentReview(
        sort: String,
        page: Int,
        size: Int
    ): ResponseGetRecentReviewDto {
        return reviewApiService.getRecentReview(
            sort = sort
        )
    }

    override suspend fun getBookReviews(
        bookId: Long,
        page: Int,
        size: Int
    ): ResponseGetBookReviewsDto {
        return reviewApiService.getBookReviews(
            bookId = bookId,
            page = page,
            size = size
        )
    }

    override suspend fun getReviewDetail(
        reviewId: Long
    ): ResponseGetReviewDetailDto {
        return reviewApiService.getReviewDetail(
            reviewId = reviewId
        )
    }

    override suspend fun addReviewLike(
        reviewId: Long
    ): ResponseReviewLikeDto {
        return reviewApiService.addReviewLike(
            reviewId = reviewId
        )
    }

    override suspend fun removeReviewLike(
        reviewId: Long
    ): ResponseReviewLikeDto {
        return reviewApiService.removeReviewLike(
            reviewId = reviewId
        )
    }

    override suspend fun getReviewComments(
        reviewId: Long,
        page: Int,
        size: Int
    ): ResponseGetReviewCommentsDto {
        return reviewApiService.getReviewComments(
            reviewId = reviewId,
            page = page,
            size = size
        )
    }

    override suspend fun createReview(
        bookId: Long,
        request: RequestCreateReviewDto
    ): ResponseCreateReviewDto {
        return reviewApiService.createReview(
            bookId = bookId,
            request = request
        )
    }

    override suspend fun createReviewComment(
        reviewId: Long,
        request: RequestCreateCommentDto
    ): ResponsePostCreateCommentDto {
        return reviewApiService.createReviewComment(reviewId, request)
    }
}