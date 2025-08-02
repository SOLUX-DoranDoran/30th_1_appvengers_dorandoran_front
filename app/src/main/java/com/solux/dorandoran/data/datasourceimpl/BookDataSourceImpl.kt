package com.solux.dorandoran.data.datasourceimpl


import com.solux.dorandoran.data.dto.response.ResponseGetBookDto
import com.solux.dorandoran.data.datasource.BookDataSource
import com.solux.dorandoran.data.service.BookApiService
import javax.inject.Inject

class BookDataSourceImpl @Inject constructor(
    private val bookApiService: BookApiService
) : BookDataSource {
    override suspend fun getBookInfo(bookId: Long): ResponseGetBookDto {
        return bookApiService.getBookInfo(
            bookId = bookId,
            authorization = "Bearer ${getAccessToken()}" // 토큰 처리 필요
        )
    }

    private fun getAccessToken(): String {
        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTA0NDM0Nzk3NDYyMjYwOTcxMjIiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzU0OTk5OTUyfQ.WvghkrfFxUIWnQjwVS8OJHx_LQrnnxldh9A7nUG26is" // 임시!
    }
}