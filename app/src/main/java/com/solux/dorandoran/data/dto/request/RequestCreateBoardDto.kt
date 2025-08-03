package com.solux.dorandoran.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCreateBoardDto(
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("bookTitle") val bookTitle: String // 수정: bookId가 아닌 bookTitle
)