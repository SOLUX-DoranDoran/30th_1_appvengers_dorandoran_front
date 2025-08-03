package com.solux.dorandoran.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCreateDiscussCommentDto(
    @SerialName("content") val content: String,
    @SerialName("parentId") val parentId: Int? = null // null이면 원댓글, 값이 있으면 대댓글
)