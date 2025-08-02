package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCreateReviewDto(
    @SerialName("reviewId") val reviewId: Long,
    @SerialName("message") val message: String
)