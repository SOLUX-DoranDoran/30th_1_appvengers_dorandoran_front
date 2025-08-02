package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostQuoteDto(
    @SerialName("quoteId") val quoteId: Long,
    @SerialName("bookId") val bookId: Long,
    @SerialName("message") val message: String
)