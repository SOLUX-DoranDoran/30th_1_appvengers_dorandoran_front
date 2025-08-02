package com.solux.dorandoran.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody

@Serializable
data class UserProfileChangeRequestDto (
    @SerialName("image") val image: String //file 타입 정의 불가, dto 넘기기 불가능
)