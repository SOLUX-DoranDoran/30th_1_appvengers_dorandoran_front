package com.solux.dorandoran.data.mapper

import com.solux.dorandoran.data.dto.response.ResponseGetBoardsDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookBoardsDto
import com.solux.dorandoran.domain.entity.PagedBoardsEntity

// 전체 토론 목록 페이지 -> PagedBoardsEntity
// 수정: 페이지네이션된 전체 토론 목록 변환
fun ResponseGetBoardsDto.toPagedBoardsEntity(): PagedBoardsEntity = PagedBoardsEntity(
    content = this.content.toBoardEntityList(),
    totalElements = this.totalElements,
    totalPages = this.totalPages,
    size = this.size,
    number = this.number,
    first = this.first,
    last = this.last,
    numberOfElements = this.numberOfElements,
    empty = this.empty
)

// 수정: 페이지네이션된 도서별 토론 목록 변환
fun ResponseGetBookBoardsDto.toPagedBookBoardsEntity(): PagedBoardsEntity = PagedBoardsEntity(
    content = this.content.toBookBoardEntityList(), // 수정: 변경된 함수명 사용
    totalElements = this.totalElements,
    totalPages = this.totalPages,
    size = this.size,
    number = this.number,
    first = this.first,
    last = this.last,
    numberOfElements = this.numberOfElements,
    empty = this.empty
)