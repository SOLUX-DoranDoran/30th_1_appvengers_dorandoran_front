package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetDiscussionDto(
    @SerialName("boardId") val boardId: Long,
    @SerialName("bookId") val bookId: Long,
    @SerialName("memberId") val memberId: Long,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("createdAt") val createdAt: String
)