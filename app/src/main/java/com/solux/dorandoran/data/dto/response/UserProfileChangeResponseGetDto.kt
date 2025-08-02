package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileChangeResponseGetDto (
    @SerialName("success") val success: Boolean,
    @SerialName("fileName") val fileName: String,
    @SerialName("profileImageUrl") val profileImageUrl: String,
)