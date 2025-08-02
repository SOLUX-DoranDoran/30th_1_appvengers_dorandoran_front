package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.BookReviewsResponseEntity
import com.solux.dorandoran.domain.entity.CommentCreateResponseEntity
import com.solux.dorandoran.domain.entity.ReviewCommentsResponseEntity
import com.solux.dorandoran.domain.entity.ReviewCreateResponseEntity
import com.solux.dorandoran.domain.entity.ReviewDetailEntity
import com.solux.dorandoran.domain.entity.ReviewEntity
import com.solux.dorandoran.domain.entity.ReviewLikeResponseEntity
import com.solux.dorandoran.domain.entity.ReviewListEntity

interface ReviewRepository {

    suspend fun getRecentReviews(
        sort: String = "recent",
        page: Int = 1,
        size: Int = 10
    ): Result<List<ReviewListEntity>>

    suspend fun getRecentReview(
        sort: String = "recent"
    ): Result<ReviewEntity?>

    suspend fun getBookReviews(
        bookId: Long,
        page: Int = 1,
        size: Int = 10
    ): Result<BookReviewsResponseEntity>

    suspend fun getReviewDetail(
        reviewId: Long
    ): Result<ReviewDetailEntity>

    suspend fun toggleReviewLike(
        reviewId: Long
    ): Result<ReviewLikeResponseEntity>

    suspend fun getReviewComments(
        reviewId: Long,
        page: Int = 1,
        size: Int = 10
    ): Result<ReviewCommentsResponseEntity>

    suspend fun createReview(
        bookId: Long,
        content: String,
        rating: Int
    ): Result<ReviewCreateResponseEntity>

    suspend fun createReviewComment(
        reviewId: Long,
        content: String
    ): Result<CommentCreateResponseEntity>
}