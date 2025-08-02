package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetBookDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BookApiService {
    @GET("/api/books/{bookId}")
    suspend fun getBookInfo(
        @Path("bookId") bookId: Long,
        @Header("Authorization") authorization: String
    ): ResponseGetBookDto

}