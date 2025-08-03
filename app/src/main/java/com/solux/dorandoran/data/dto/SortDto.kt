package com.solux.dorandoran.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SortDto(
    @SerialName("empty") val empty: Boolean,
    @SerialName("unsorted") val unsorted: Boolean,
    @SerialName("sorted") val sorted: Boolean
)