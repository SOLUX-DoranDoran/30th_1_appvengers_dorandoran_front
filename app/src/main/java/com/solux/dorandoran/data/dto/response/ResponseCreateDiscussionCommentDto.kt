package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCreateDiscussCommentDto(
    @SerialName("id") val id: Int,
    @SerialName("content") val content: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("memberNickname") val memberNickname: String,
    @SerialName("parentId") val parentId: Int?
)