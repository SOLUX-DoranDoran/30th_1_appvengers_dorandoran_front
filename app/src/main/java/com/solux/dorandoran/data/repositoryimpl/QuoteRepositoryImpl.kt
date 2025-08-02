package com.solux.dorandoran.data.repositoryimpl

import android.content.Context
import com.solux.dorandoran.data.datasource.QuoteDataSource
import com.solux.dorandoran.data.dto.request.RequestPostQuoteDto
import com.solux.dorandoran.data.mapper.toQuoteEntity
import com.solux.dorandoran.data.mapper.toQuoteLikeResponseEntity
import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.entity.QuoteLikeResponseEntity
import com.solux.dorandoran.domain.repository.QuoteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val quoteDataSource: QuoteDataSource,
    @ApplicationContext private val context: Context
) : QuoteRepository {

    private suspend fun getAccessToken(): String {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is"
    }

    override suspend fun postQuote(content: String, bookId: Long): Result<Long> {
        return runCatching {
            val token = getAccessToken()
            val request = RequestPostQuoteDto(content = content, bookId = bookId)
            val response = quoteDataSource.postQuote(token, request)
            response.quoteId
        }
    }

    override suspend fun getQuotes(
        sort: String,
        page: Int,
        size: Int
    ): Result<List<QuoteEntity>> {
        return runCatching {
            val token = getAccessToken()
            val response = quoteDataSource.getQuotes(token, sort, page, size)
            response.map { it.toQuoteEntity() }
        }
    }

    override suspend fun getRecentQuote(): Result<QuoteEntity?> {
        return runCatching {
            val token = getAccessToken()
            val response = quoteDataSource.getRecentQuote(token)
            response.firstOrNull()?.toQuoteEntity()
        }
    }

    override suspend fun toggleQuoteLike(
        quoteId: Long
    ): Result<QuoteLikeResponseEntity> {
        return runCatching {
            val token = getAccessToken()

            try {
                val response = quoteDataSource.addQuoteLike(
                    token = token,
                    quoteId = quoteId
                )
                response.toQuoteLikeResponseEntity()
            } catch (e: Exception) {
                val response = quoteDataSource.removeQuoteLike(
                    token = token,
                    quoteId = quoteId
                )
                response.toQuoteLikeResponseEntity()
            }
        }
    }
}