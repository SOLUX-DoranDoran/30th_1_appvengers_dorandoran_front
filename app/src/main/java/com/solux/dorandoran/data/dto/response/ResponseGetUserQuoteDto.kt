package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetUserQuoteDto(
    @SerialName("id") val id: Long,
    @SerialName("bookName") val bookName: String,
    @SerialName("content") val content: String,
    @SerialName("likeCount") val likeCount: Int,
    @SerialName("createdAt") val createdAt: String
)