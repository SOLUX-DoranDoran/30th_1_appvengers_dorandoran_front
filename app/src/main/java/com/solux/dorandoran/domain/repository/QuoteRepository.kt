package com.solux.dorandoran.domain.repository

import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.entity.QuoteLikeResponseEntity

interface QuoteRepository {
    suspend fun postQuote(
        content: String, bookId: Long
    ): Result<Long>

    suspend fun getQuotes(
        sort: String = "recent",
        page: Int = 1,
        size: Int = 10
    ): Result<List<QuoteEntity>>

    suspend fun getRecentQuote(): Result<QuoteEntity?>

    suspend fun toggleQuoteLike(
        quoteId: Long
    ): Result<QuoteLikeResponseEntity>
}