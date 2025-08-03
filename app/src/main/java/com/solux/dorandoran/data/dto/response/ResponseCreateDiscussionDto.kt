package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCreateDiscussionDto(
    @SerialName("boardId") val boardId: Int,
    @SerialName("message") val message: String
)