package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetReviewCommentsDto(
    @SerialName("totalComments") val totalComments: Int,
    @SerialName("comments") val comments: List<ResponseGetCommentItemDto>,
    @SerialName("currentPage") val currentPage: Int,
    @SerialName("totalPages") val totalPages: Int
)