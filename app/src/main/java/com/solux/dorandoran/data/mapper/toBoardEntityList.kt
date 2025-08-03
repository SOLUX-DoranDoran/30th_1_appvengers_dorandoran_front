package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetBoardItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookBoardItemDto
import com.solux.dorandoran.domain.entity.BoardEntity

// 수정: 전체 토론 목록 변환 (ResponseGetBoardItemDto용)
fun List<ResponseGetBoardItemDto>.toBoardEntityList(): List<BoardEntity> =
    this.map { it.toBoardEntity() }

// 수정: 도서별 토론 목록 변환 (ResponseGetBookBoardItemDto용) - 함수명 변경
fun List<ResponseGetBookBoardItemDto>.toBookBoardEntityList(): List<BoardEntity> =
    this.map { it.toBoardEntity() }