package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.request.RequestPostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseGetQuoteDto
import com.solux.dorandoran.data.dto.response.ResponsePostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseQuoteLikeDto

interface QuoteDataSource {
    suspend fun postQuote(
        request: RequestPostQuoteDto
    ): ResponsePostQuoteDto

    suspend fun getQuotes(
        sort: String,
        page: Int,
        size: Int
    ): List<ResponseGetQuoteDto>

    suspend fun getRecentQuote(): List<ResponseGetQuoteDto>

    suspend fun addQuoteLike(
        quoteId: Long
    ): ResponseQuoteLikeDto

    suspend fun removeQuoteLike(
        quoteId: Long
    ): ResponseQuoteLikeDto
}