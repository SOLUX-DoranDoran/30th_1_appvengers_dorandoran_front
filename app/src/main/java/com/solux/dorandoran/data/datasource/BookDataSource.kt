package com.solux.dorandoran.data.datasource

import com.solux.dorandoran.data.dto.response.ResponseGetBookDto

interface BookDataSource {
    suspend fun getBookInfo(bookId: Long): ResponseGetBookDto
}