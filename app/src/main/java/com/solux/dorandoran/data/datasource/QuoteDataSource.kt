package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.request.RequestPostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseGetQuoteDto
import com.solux.dorandoran.data.dto.response.ResponsePostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseQuoteLikeDto

interface QuoteDataSource {
    suspend fun postQuote(
        token: String,
        request: RequestPostQuoteDto
    ): ResponsePostQuoteDto

    suspend fun getQuotes(
        token: String,
        sort: String,
        page: Int,
        size: Int
    ): List<ResponseGetQuoteDto>

    suspend fun getRecentQuote(
        token: String
    ): List<ResponseGetQuoteDto>

    suspend fun addQuoteLike(
        token: String,
        quoteId: Long
    ): ResponseQuoteLikeDto

    suspend fun removeQuoteLike(
        token: String,
        quoteId: Long
    ): ResponseQuoteLikeDto
}