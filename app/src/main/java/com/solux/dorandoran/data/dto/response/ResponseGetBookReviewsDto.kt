package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetBookReviewsDto(
    @SerialName("reviews") val reviews: List<ResponseGetBookReviewItemDto>,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("currentPage") val currentPage: Int,
    @SerialName("totalReviews") val totalReviews: Int
)