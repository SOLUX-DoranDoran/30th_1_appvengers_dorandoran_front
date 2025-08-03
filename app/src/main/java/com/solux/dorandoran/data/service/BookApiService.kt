package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetBookDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET("/api/books/{bookId}")
    suspend fun getBookInfo(
        @Path("bookId") bookId: Long
    ): ResponseGetBookDto

    // 도서 정보 조회
    @GET("/api/books/{bookId}")
    suspend fun getBookDetail(
        @Path("bookId") bookId: Int
    ): ResponseGetBookDto
}