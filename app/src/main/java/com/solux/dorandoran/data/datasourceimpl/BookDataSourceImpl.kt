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
            bookId = bookId
        )
    }
}