package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetRecommendedBookDto (
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("author") val author: String,
    @SerialName("publisher") val publisher: String,
    @SerialName("publisherDate") val publisherDate: String,
    @SerialName("coverImageUrl") val coverImageUrl: String
)