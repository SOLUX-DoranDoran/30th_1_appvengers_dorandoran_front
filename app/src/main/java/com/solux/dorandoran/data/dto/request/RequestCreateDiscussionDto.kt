package com.solux.dorandoran.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCreateDiscussionDto(
    @SerialName("bookId") val bookId: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String
)