package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseQuoteLikeDto
import com.solux.dorandoran.domain.entity.QuoteLikeResponseEntity

fun ResponseQuoteLikeDto.toQuoteLikeResponseEntity() = QuoteLikeResponseEntity(
    success = success,
    message = message,
    likeCount = likeCount
)