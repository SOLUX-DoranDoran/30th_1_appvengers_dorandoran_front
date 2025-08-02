package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetBookReviewItemDto(
    @SerialName("reviewId") val reviewId: Long,
    @SerialName("bookId") val bookId: Long,
    @SerialName("bookTitle") val bookTitle: String,
    @SerialName("coverImageUrl") val coverImageUrl: String,
    @SerialName("content") val content: String,
    @SerialName("rating") val rating: Int,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImage") val profileImage: String?
)