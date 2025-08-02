package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetUserDto
import com.solux.dorandoran.domain.entity.UserEntity

fun ResponseGetUserDto.toUserEntity() = UserEntity(
    id = id,
    email = email,
    nickname = nickname,
    profileImage = profileImage,
    createdAt = createdAt
)