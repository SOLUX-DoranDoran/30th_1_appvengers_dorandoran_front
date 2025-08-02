package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetCommentItemDto(
    @SerialName("id") val id: Long,
    @SerialName("parentCommentNickname") val parentCommentNickname: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImage") val profileImage: String?,
    @SerialName("content") val content: String,
    @SerialName("createdAt") val createdAt: String
)