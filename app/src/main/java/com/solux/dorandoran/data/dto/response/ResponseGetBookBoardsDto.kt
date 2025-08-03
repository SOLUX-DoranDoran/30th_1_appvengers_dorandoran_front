package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetBookBoardsDto(
    @SerialName("content") val content: List<ResponseGetBookBoardItemDto>,
    @SerialName("pageable") val pageable: PageableDto,
    @SerialName("last") val last: Boolean,
    @SerialName("totalElements") val totalElements: Int,
    @SerialName("totalPages") val totalPages: Int,
    @SerialName("size") val size: Int,
    @SerialName("number") val number: Int,
    @SerialName("first") val first: Boolean,
    @SerialName("numberOfElements") val numberOfElements: Int,
    @SerialName("sort") val sort: SortDto,
    @SerialName("empty") val empty: Boolean
)