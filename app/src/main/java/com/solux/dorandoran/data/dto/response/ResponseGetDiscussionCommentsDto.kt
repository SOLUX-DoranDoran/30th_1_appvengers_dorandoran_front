package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetDiscussCommentsDto(
    @SerialName("content") val content: List<ResponseGetDiscussCommentItemDto>,
    @SerialName("pageable") val pageable: PageableDto,
    @SerialName("sort") val sort: SortDto,
    @SerialName("last") val last: Boolean,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("totalElements") val totalElements: Int,
    @SerialName("size") val size: Int,
    @SerialName("number") val number: Int,
    @SerialName("first") val first: Boolean,
    @SerialName("numberOfElements") val numberOfElements: Int,
    @SerialName("empty") val empty: Boolean
)