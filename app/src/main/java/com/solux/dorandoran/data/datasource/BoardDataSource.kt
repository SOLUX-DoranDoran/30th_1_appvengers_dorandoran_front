package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.request.RequestCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardDetailDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardsDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookBoardsDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookDto

interface BoardDataSource {

    // 전체 토론 목록 조회
    suspend fun getBoards(
        page: Int,
        size: Int
    ): ResponseGetBoardsDto

    // 토론 생성
    suspend fun createBoard(
        title: String,
        content: String,
        bookTitle: String
    ): ResponseCreateBoardDto

    // 토론 상세 조회
    suspend fun getBoardDetail(
        boardId: Int
    ): ResponseGetBoardDetailDto

    // 도서별 토론 목록 조회
    suspend fun getBookBoards(
        bookId: Int,
        page: Int,
        size: Int
    ): ResponseGetBookBoardsDto

    // 도서 정보 조회
    suspend fun getBookDetail(
        bookId: Int
    ): ResponseGetBookDto
}