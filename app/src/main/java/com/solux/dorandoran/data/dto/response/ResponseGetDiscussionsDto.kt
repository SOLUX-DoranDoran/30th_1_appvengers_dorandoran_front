package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetDiscussionsDto(
    @SerialName("content") val content: List<ResponseGetDiscussionItemDto>,
    @SerialName("pageable") val pageable: PageableDto,
    @SerialName("sort") val sort: SortDto,
    @SerialName("first") val first: Boolean,
    @SerialName("last") val last: Boolean,
    @SerialName("empty") val empty: Boolean,
    @SerialName("totalElements") val totalElements: Int? = null,
    @SerialName("totalPages") val totalPages: Int? = null,
    @SerialName("size") val size: Int? = null,
    @SerialName("number") val number: Int? = null
)