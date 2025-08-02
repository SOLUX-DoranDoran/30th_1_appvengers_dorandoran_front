package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import com.solux.dorandoran.data.datasource.ReviewDataSource
import com.solux.dorandoran.data.dto.request.RequestCreateCommentDto
import com.solux.dorandoran.data.dto.request.RequestCreateReviewDto
import com.solux.dorandoran.data.mapper.toBookReviewsResponseEntity
import com.solux.dorandoran.data.mapper.toReviewCommentsResponseEntity
import com.solux.dorandoran.data.mapper.toReviewDetailEntity
import com.solux.dorandoran.data.mapper.toReviewLikeResponseEntity
import com.solux.dorandoran.data.mapper.toReviewListEntity
import com.solux.dorandoran.domain.entity.BookReviewsResponseEntity
import com.solux.dorandoran.domain.entity.CommentCreateResponseEntity
import com.solux.dorandoran.domain.entity.ReviewCommentsResponseEntity
import com.solux.dorandoran.domain.entity.ReviewCreateResponseEntity
import com.solux.dorandoran.domain.entity.ReviewDetailEntity
import com.solux.dorandoran.domain.entity.ReviewLikeResponseEntity
import com.solux.dorandoran.domain.entity.ReviewListEntity
import com.solux.dorandoran.domain.repository.ReviewRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.solux.dorandoran.domain.entity.ReviewEntity
import com.solux.dorandoran.data.mapper.toReviewEntity

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource,
    @ApplicationContext private val context: Context
) : ReviewRepository {

    private suspend fun getAccessToken(): String {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
    }

    override suspend fun getRecentReviews(
        sort: String,
        page: Int,
        size: Int
    ): Result<List<ReviewListEntity>> {
        return runCatching {
            val token = getAccessToken()
            val response = reviewDataSource.getRecentReviews(
                token = token,
                sort = sort,
                page = page,
                size = size
            )
            response.map { it.toReviewListEntity() }
        }
    }

    override suspend fun getRecentReview(
        sort: String
    ): Result<ReviewEntity?> {
        return runCatching {
            val token = getAccessToken()
            val response = reviewDataSource.getRecentReview(
                token = token,
                sort = sort,
                page = 1,
                size = 1
            )
            response.toReviewEntity()
        }
    }

    override suspend fun getBookReviews(
        bookId: Long,
        page: Int,
        size: Int
    ): Result<BookReviewsResponseEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = reviewDataSource.getBookReviews(
                token = token,
                bookId = bookId,
                page = page,
                size = size
            )
            response.toBookReviewsResponseEntity()
        }
    }

    override suspend fun getReviewDetail(
        reviewId: Long
    ): Result<ReviewDetailEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = reviewDataSource.getReviewDetail(
                token = token,
                reviewId = reviewId
            )
            response.toReviewDetailEntity()
        }
    }

    override suspend fun toggleReviewLike(
        reviewId: Long
    ): Result<ReviewLikeResponseEntity> {
        return runCatching {
            val token = getAccessToken()

            try {
                val response = reviewDataSource.addReviewLike(
                    token = token,
                    reviewId = reviewId
                )
                response.toReviewLikeResponseEntity()
            } catch (e: Exception) {
                val response = reviewDataSource.removeReviewLike(
                    token = token,
                    reviewId = reviewId
                )
                response.toReviewLikeResponseEntity()
            }
        }
    }

    override suspend fun getReviewComments(
        reviewId: Long,
        page: Int,
        size: Int
    ): Result<ReviewCommentsResponseEntity> {
        return runCatching {
            val token = getAccessToken()
            val response = reviewDataSource.getReviewComments(
                token = token,
                reviewId = reviewId,
                page = page,
                size = size
            )
            response.toReviewCommentsResponseEntity()
        }
    }

    override suspend fun createReview(
        bookId: Long,
        content: String,
        rating: Int
    ): Result<ReviewCreateResponseEntity> {
        return runCatching {
            val token = getAccessToken()
            val request = RequestCreateReviewDto(
                content = content,
                rating = rating
            )
            val response = reviewDataSource.createReview(
                token = token,
                bookId = bookId,
                request = request
            )
            ReviewCreateResponseEntity(
                reviewId = response.reviewId,
                message = response.message
            )
        }
    }

    override suspend fun createReviewComment(
        reviewId: Long,
        content: String
    ): Result<CommentCreateResponseEntity> {
        return runCatching {
            val token = getAccessToken()
            val request = RequestCreateCommentDto(content = content)
            val response = reviewDataSource.createReviewComment(
                token = token,
                reviewId = reviewId,
                request = request
            )
            CommentCreateResponseEntity(
                commentId = response.commentId,
                message = response.message
            )
        }
    }
}