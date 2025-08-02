package com.solux.dorandoran.data.repositoryimpl

import com.solux.dorandoran.data.datasource.QuoteDataSource
import com.solux.dorandoran.data.dto.request.RequestPostQuoteDto
import com.solux.dorandoran.data.mapper.toQuoteEntity
import com.solux.dorandoran.data.mapper.toQuoteLikeResponseEntity
import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.entity.QuoteLikeResponseEntity
import com.solux.dorandoran.domain.repository.QuoteRepository
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteDataSource: QuoteDataSource,
) : QuoteRepository {
    override suspend fun postQuote(content: String, bookId: Long): Result<Long> {
        return runCatching {
            val request = RequestPostQuoteDto(content = content, bookId = bookId)
            val response = quoteDataSource.postQuote(request)
            response.quoteId
        }
    }

    override suspend fun getQuotes(
        sort: String,
        page: Int,
        size: Int
    ): Result<List<QuoteEntity>> {
        return runCatching {
            val response = quoteDataSource.getQuotes(sort, page, size)
            response.map { it.toQuoteEntity() }
        }
    }

    override suspend fun getRecentQuote(): Result<QuoteEntity?> {
        return runCatching {
            val response = quoteDataSource.getRecentQuote()
            response.firstOrNull()?.toQuoteEntity()
        }
    }

    override suspend fun toggleQuoteLike(
        quoteId: Long
    ): Result<QuoteLikeResponseEntity> {
        return runCatching {
            try {
                val response = quoteDataSource.addQuoteLike(
                    quoteId = quoteId
                )
                response.toQuoteLikeResponseEntity()
            } catch (e: Exception) {
                val response = quoteDataSource.removeQuoteLike(
                    quoteId = quoteId
                )
                response.toQuoteLikeResponseEntity()
            }
        }
    }
}