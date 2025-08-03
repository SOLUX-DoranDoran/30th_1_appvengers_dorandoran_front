package com.solux.dorandoran.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageableDto(
    @SerialName("pageNumber") val pageNumber: Int,
    @SerialName("pageSize") val pageSize: Int,
    @SerialName("sort") val sort: SortDto,
    @SerialName("offset") val offset: Int,
    @SerialName("unpaged") val unpaged: Boolean,
    @SerialName("paged") val paged: Boolean
)