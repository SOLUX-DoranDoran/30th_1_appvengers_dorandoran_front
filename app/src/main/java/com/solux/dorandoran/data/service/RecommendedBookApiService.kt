package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.response.ResponseGetRecommendedBookDto
import retrofit2.http.GET

interface RecommendedBookApiService {
    @GET("/api/books/recommendations")
    suspend fun getRecommendedBooks(): List<ResponseGetRecommendedBookDto>
}