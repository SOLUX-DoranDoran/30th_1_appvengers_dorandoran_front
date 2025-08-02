package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.RecommendedBookDataSource
import com.solux.dorandoran.data.dto.response.ResponseGetRecommendedBookDto
import com.solux.dorandoran.data.service.RecommendedBookApiService
import javax.inject.Inject

class RecommendedBookDataSourceImpl @Inject constructor(
    private val recommendedBookApiService: RecommendedBookApiService
) : RecommendedBookDataSource {
    override suspend fun getRecommendedBooks(): List<ResponseGetRecommendedBookDto> {
        return recommendedBookApiService.getRecommendedBooks()
    }
}