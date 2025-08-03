package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class StorageImgErrorResponseDto(
    @SerialName("message") val message: String,
    @SerialName("success") val success: Boolean
)