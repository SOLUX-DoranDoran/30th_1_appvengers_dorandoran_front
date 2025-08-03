package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.BoardDataSource
import com.solux.dorandoran.data.dto.request.RequestCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardDetailDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardsDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookBoardsDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookDto
import com.solux.dorandoran.data.service.BoardApiService
import com.solux.dorandoran.data.service.BookApiService
import javax.inject.Inject

class BoardDataSourceImpl @Inject constructor(
    private val boardApiService: BoardApiService,
    private val bookApiService: BookApiService // 수정: 도서 정보 조회용 추가
) : BoardDataSource {

    override suspend fun getBoards(
        page: Int,
        size: Int
    ): ResponseGetBoardsDto {
        // 수정: AuthInterceptor에서 자동으로 토큰 추가하므로 토큰 파라미터 제거
        return boardApiService.getBoards(
            page = page,
            size = size
        )
    }

    override suspend fun createBoard(
        title: String,
        content: String,
        bookTitle: String
    ): ResponseCreateBoardDto {
        // 수정: AuthInterceptor에서 자동으로 토큰 추가
        return boardApiService.createBoard(
            request = RequestCreateBoardDto(
                title = title,
                content = content,
                bookTitle = bookTitle
            )
        )
    }

    override suspend fun getBoardDetail(
        boardId: Int
    ): ResponseGetBoardDetailDto {
        // 수정: AuthInterceptor에서 자동으로 토큰 추가
        return boardApiService.getBoardDetail(boardId = boardId)
    }

    override suspend fun getBookBoards(
        bookId: Int,
        page: Int,
        size: Int
    ): ResponseGetBookBoardsDto {
        // 수정: AuthInterceptor에서 자동으로 토큰 추가
        return boardApiService.getBookBoards(
            bookId = bookId,
            page = page,
            size = size
        )
    }

    override suspend fun getBookDetail(
        bookId: Int
    ): ResponseGetBookDto {
        // 수정: AuthInterceptor에서 자동으로 토큰 추가
        return bookApiService.getBookDetail(bookId = bookId)
    }
}