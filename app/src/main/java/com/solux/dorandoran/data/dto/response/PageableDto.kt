package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageableDto(
    @SerialName("sort") val sort: SortDto,
    @SerialName("offset") val offset: Int,
    @SerialName("pageSize") val pageSize: Int,
    @SerialName("pageNumber") val pageNumber: Int,
    @SerialName("paged") val paged: Boolean,
    @SerialName("unpaged") val unpaged: Boolean
)