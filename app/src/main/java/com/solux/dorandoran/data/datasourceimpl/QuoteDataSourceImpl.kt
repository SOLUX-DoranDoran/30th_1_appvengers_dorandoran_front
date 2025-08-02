package com.solux.dorandoran.data.datasourceimpl

import com.solux.dorandoran.data.datasource.QuoteDataSource
import com.solux.dorandoran.data.dto.request.RequestPostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseGetQuoteDto
import com.solux.dorandoran.data.dto.response.ResponsePostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseQuoteLikeDto
import com.solux.dorandoran.data.service.QuoteApiService
import javax.inject.Inject

class QuoteDataSourceImpl @Inject constructor(
    private val quoteApiService: QuoteApiService
) : QuoteDataSource {
    override suspend fun postQuote(
        request: RequestPostQuoteDto
    ): ResponsePostQuoteDto {
        return quoteApiService.postQuote(request)
    }

    override suspend fun getQuotes(
        sort: String,
        page: Int,
        size: Int
    ): List<ResponseGetQuoteDto> {
        return quoteApiService.getQuotes(sort, page, size)
    }

    override suspend fun getRecentQuote(): List<ResponseGetQuoteDto> {
        return quoteApiService.getRecentQuote()
    }

    // 수정: 좋아요 기능 추가 (Review 방식과 동일)
    override suspend fun addQuoteLike(
        quoteId: Long
    ): ResponseQuoteLikeDto {
        return quoteApiService.addQuoteLike(
            quoteId = quoteId
        )
    }

    override suspend fun removeQuoteLike(
        quoteId: Long
    ): ResponseQuoteLikeDto {
        return quoteApiService.removeQuoteLike(
            quoteId = quoteId
        )
    }
}