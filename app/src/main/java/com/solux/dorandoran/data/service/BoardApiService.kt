// 수정: API 명세에 맞는 새로운 BoardApiService
package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.request.RequestCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseCreateBoardDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardDetailDto
import com.solux.dorandoran.data.dto.response.ResponseGetBoardsDto
import com.solux.dorandoran.data.dto.response.ResponseGetBookBoardsDto
import retrofit2.http.*

interface BoardApiService {

    // 전체 토론 목록 조회
    @GET("/api/boards")
    suspend fun getBoards(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ResponseGetBoardsDto

    // 토론 생성
    @POST("/api/boards")
    suspend fun createBoard(
        @Body request: RequestCreateBoardDto
    ): ResponseCreateBoardDto

    // 토론 상세 조회
    @GET("/api/boards/{boardId}")
    suspend fun getBoardDetail(
        @Path("boardId") boardId: Int
    ): ResponseGetBoardDetailDto

    // 도서별 토론 목록 조회
    @GET("/api/books/{bookId}/boards")
    suspend fun getBookBoards(
        @Path("bookId") bookId: Int,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): ResponseGetBookBoardsDto
}