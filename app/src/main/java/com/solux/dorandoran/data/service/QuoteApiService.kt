package com.solux.dorandoran.data.service

import com.solux.dorandoran.data.dto.request.RequestPostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseGetQuoteDto
import com.solux.dorandoran.data.dto.response.ResponsePostQuoteDto
import com.solux.dorandoran.data.dto.response.ResponseQuoteLikeDto
import retrofit2.http.*

interface QuoteApiService {
    @POST("/api/quotes")
    suspend fun postQuote(
        @Body request: RequestPostQuoteDto
    ): ResponsePostQuoteDto

    @GET("/api/quotes")
    suspend fun getQuotes(
        @Query("sort") sort: String = "recent",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): List<ResponseGetQuoteDto>

    @GET("/api/quotes/recent")
    suspend fun getRecentQuote(): List<ResponseGetQuoteDto>

    @POST("/api/quotes/{quoteId}/like")
    suspend fun addQuoteLike(
        @Path("quoteId") quoteId: Long
    ): ResponseQuoteLikeDto

    @DELETE("/api/quotes/{quoteId}/like")
    suspend fun removeQuoteLike(
        @Path("quoteId") quoteId: Long
    ): ResponseQuoteLikeDto
}