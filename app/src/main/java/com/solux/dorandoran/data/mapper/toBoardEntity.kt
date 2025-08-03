package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardDetailDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardItemDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookBoardItemDto
import com.solux.dorandoran.domain.entity.BoardEntity

// 개별 DTO → Entity 변환 함수들
fun ResponseGetBoardItemDto.toBoardEntity(): BoardEntity = BoardEntity(
    id = this.boardId,
    bookId = this.bookId,
    memberId = this.memberId,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt
)

fun ResponseGetBookBoardItemDto.toBoardEntity(): BoardEntity = BoardEntity(
    id = this.boardId, // 수정: ResponseGetBookBoardItemDto에서는 boardId 필드명 사용
    bookId = this.bookId,
    memberId = this.memberId,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt
)

fun ResponseCreateBoardDto.toBoardEntity(): BoardEntity = BoardEntity(
    id = this.boardId, // 수정: 생성 응답에서는 boardId 필드명 사용
    bookId = this.bookId,
    memberId = this.memberId,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt
)

fun ResponseGetBoardDetailDto.toBoardEntity(): BoardEntity = BoardEntity(
    id = this.boardId, // 수정: 상세 조회에서는 boardId 필드명 사용
    bookId = this.bookId,
    memberId = this.memberId,
    title = this.title,
    content = this.content,
    createdAt = this.createdAt
)