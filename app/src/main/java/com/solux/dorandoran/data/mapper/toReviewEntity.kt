package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetRecentReviewDto
import com.solux.dorandoran.domain.entity.ReviewEntity

fun ResponseGetRecentReviewDto.toReviewEntity(): ReviewEntity {
    return ReviewEntity(
        id = this.reviewId,
        bookTitle = this.bookTitle,
        coverImageUrl = this.coverImageUrl,
        content = this.content,
        rating = this.rating,
        createdAt = this.createdAt,
        nickname = this.nickname,
        profileImage = this.profileImage ?: "", // nullable이므로 기본값 처리
        bookId = this.bookId,
        userId = 0L, // ResponseDto에 없는 필드이므로 기본값
        likeCount = 0, // ResponseDto에 없는 필드이므로 기본값
        commentCount = 0, // ResponseDto에 없는 필드이므로 기본값
        isLiked = false // ResponseDto에 없는 필드이므로 기본값
    )
}