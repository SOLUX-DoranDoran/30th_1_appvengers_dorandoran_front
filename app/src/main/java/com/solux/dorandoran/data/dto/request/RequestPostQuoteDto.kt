package com.solux.dorandoran.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostQuoteDto (
    @SerialName("content") val content: String,
    @SerialName("bookId") val bookId: Long
)