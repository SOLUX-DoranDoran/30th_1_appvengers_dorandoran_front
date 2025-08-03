package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetBoardItemDto(
    @SerialName("boardId") val boardId: Int, // 수정: boardId가 아닌 id
    @SerialName("bookId") val bookId: Int,
    @SerialName("memberId") val memberId: Int,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("createdAt") val createdAt: String
)