package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostCreateCommentDto (
    @SerialName("commentId") val commentId: Long,
    @SerialName("message") val message: String
)