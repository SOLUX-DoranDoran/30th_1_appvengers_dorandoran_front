package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.RecommendedBookDataSource
import com.solux.dorandoran.data.dto.response.ResponseGetRecommendedBookDto
import com.solux.dorandoran.data.service.RecommendedBookApiService
import javax.inject.Inject

class RecommendedBookDataSourceImpl @Inject constructor(
    private val recommendedBookApiService: RecommendedBookApiService
) : RecommendedBookDataSource {
    override suspend fun getRecommendedBooks(): List<ResponseGetRecommendedBookDto> {
        val token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
        return recommendedBookApiService.getRecommendedBooks(token)
    }
}