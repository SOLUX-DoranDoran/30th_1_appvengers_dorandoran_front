package com.solux.dorandoran.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetUserDto(
    @SerialName("id") val id: Long,
    @SerialName("email") val email: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImage") val profileImage: String?,
    @SerialName("createdAt") val createdAt: String
)
