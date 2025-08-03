package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.BoardEntity
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.entity.PagedBoardsEntity

interface BoardRepository {

    // 전체 토론 목록 조회
    suspend fun getBoards(
        page: Int,
        size: Int
    ): Result<PagedBoardsEntity>

    // 토론 생성
    suspend fun createBoard(
        title: String,
        content: String,
        bookTitle: String
    ): Result<BoardEntity>

    // 토론 상세 조회
    suspend fun getBoardDetail(
        boardId: Int
    ): Result<BoardEntity>

    // 도서별 토론 목록 조회
    suspend fun getBookBoards(
        bookId: Int,
        page: Int,
        size: Int
    ): Result<PagedBoardsEntity>

    // 도서 정보 조회
    suspend fun getBookDetail(
        bookId: Int
    ): Result<BookEntity>
}