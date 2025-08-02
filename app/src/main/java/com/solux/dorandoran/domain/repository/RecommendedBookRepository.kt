package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.RecommendedBookEntity

interface RecommendedBookRepository {
    suspend fun getRecommendedBooks() : Result<List<RecommendedBookEntity>>
}