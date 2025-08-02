package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class LogoutResponseGetDto (
    @SerialName("message") val message: String,
    @SerialName("success") val success: Boolean
)