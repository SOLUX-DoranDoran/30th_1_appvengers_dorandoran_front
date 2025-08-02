package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseQuoteLikeDto(
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
    @SerialName("likeCount") val likeCount: Int
)