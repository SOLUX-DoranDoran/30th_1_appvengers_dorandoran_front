package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseGetRecommendedBookDto

interface RecommendedBookDataSource {
    suspend fun getRecommendedBooks(): List<ResponseGetRecommendedBookDto>
}