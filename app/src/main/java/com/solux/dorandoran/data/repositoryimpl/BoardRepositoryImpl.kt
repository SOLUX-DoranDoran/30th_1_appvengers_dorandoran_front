package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import android.util.Log
import com.solux.dorandoran.data.datasource.BoardDataSource
import com.solux.dorandoran.data.mapper.toBoardEntity
import com.solux.dorandoran.data.mapper.toBookEntity
import com.solux.dorandoran.data.mapper.toPagedBoardsEntity
import com.solux.dorandoran.data.mapper.toPagedBookBoardsEntity
import com.solux.dorandoran.domain.entity.BoardEntity
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.entity.PagedBoardsEntity
import com.solux.dorandoran.domain.repository.BoardRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val boardDataSource: BoardDataSource,
    @ApplicationContext private val context: Context
) : BoardRepository {

    companion object {
        private const val TAG = "BoardRepositoryImpl"
    }

    override suspend fun getBoards(
        page: Int,
        size: Int
    ): Result<PagedBoardsEntity> {
        return runCatching {
            Log.d(TAG, "전체 토론 목록 조회 시작 - page: $page, size: $size")

            val response = boardDataSource.getBoards(page, size)
            val result = response.toPagedBoardsEntity()

            Log.d(TAG, "전체 토론 목록 조회 성공 - 총 ${result.totalElements}개, 현재 페이지: ${result.content.size}개")
            result
        }.onFailure { exception ->
            Log.e(TAG, "전체 토론 목록 조회 실패", exception)
        }
    }

    override suspend fun createBoard(
        title: String,
        content: String,
        bookTitle: String
    ): Result<BoardEntity> {
        return runCatching {
            Log.d(TAG, "토론 생성 시작 - title: $title, bookTitle: $bookTitle")

            val response = boardDataSource.createBoard(title, content, bookTitle)
            val result = response.toBoardEntity()

            Log.d(TAG, "토론 생성 성공 - boardId: ${result.id}")
            result
        }.onFailure { exception ->
            Log.e(TAG, "토론 생성 실패", exception)
        }
    }

    override suspend fun getBoardDetail(
        boardId: Int
    ): Result<BoardEntity> {
        return runCatching {
            Log.d(TAG, "토론 상세 조회 시작 - boardId: $boardId")

            val response = boardDataSource.getBoardDetail(boardId)
            val result = response.toBoardEntity()

            Log.d(TAG, "토론 상세 조회 성공 - title: ${result.title}")
            result
        }.onFailure { exception ->
            Log.e(TAG, "토론 상세 조회 실패 - boardId: $boardId", exception)
        }
    }

    override suspend fun getBookBoards(
        bookId: Int,
        page: Int,
        size: Int
    ): Result<PagedBoardsEntity> {
        return runCatching {
            Log.d(TAG, "도서별 토론 목록 조회 시작 - bookId: $bookId, page: $page, size: $size")

            val response = boardDataSource.getBookBoards(bookId, page, size)
            val result = response.toPagedBookBoardsEntity()

            Log.d(TAG, "도서별 토론 목록 조회 성공 - 총 ${result.totalElements}개, 현재 페이지: ${result.content.size}개")
            result
        }.onFailure { exception ->
            Log.e(TAG, "도서별 토론 목록 조회 실패 - bookId: $bookId", exception)
        }
    }

    override suspend fun getBookDetail(
        bookId: Int
    ): Result<BookEntity> {
        return runCatching {
            Log.d(TAG, "도서 정보 조회 시작 - bookId: $bookId")

            val response = boardDataSource.getBookDetail(bookId)
            val result = response.toBookEntity()

            Log.d(TAG, "도서 정보 조회 성공 - title: ${result.title}")
            result
        }.onFailure { exception ->
            Log.e(TAG, "도서 정보 조회 실패 - bookId: $bookId", exception)
        }
    }
}