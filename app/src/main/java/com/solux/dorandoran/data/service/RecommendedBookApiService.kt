package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetRecommendedBookDto
import retrofit2.http.GET
import retrofit2.http.Header

interface RecommendedBookApiService {
    @GET("/api/books/recommendations")
    suspend fun getRecommendedBooks(
        @Header("Authorization") authorization: String
    ): List<ResponseGetRecommendedBookDto>
}