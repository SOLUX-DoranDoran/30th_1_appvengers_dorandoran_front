package com.solux.dorandoran.data.repositoryimpl

import com.solux.dorandoran.data.datasource.RecommendedBookDataSource
import com.solux.dorandoran.data.mapper.toRecommendedBookEntity
import com.solux.dorandoran.domain.entity.RecommendedBookEntity
import com.solux.dorandoran.domain.repository.RecommendedBookRepository
import javax.inject.Inject

class RecommendedBookRepositoryImpl @Inject constructor(
    private val recommendedBookDataSource: RecommendedBookDataSource
): RecommendedBookRepository {
    override suspend fun getRecommendedBooks(): Result<List<RecommendedBookEntity>> {
        return runCatching {
            recommendedBookDataSource.getRecommendedBooks().map {
                it.toRecommendedBookEntity()
            }
        }
    }
}

