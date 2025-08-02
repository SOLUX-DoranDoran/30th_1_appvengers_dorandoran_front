package com.solux.dorandoran.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCreateReviewDto (
    @SerialName("content") val content: String,
    @SerialName("rating") val rating: Int
)