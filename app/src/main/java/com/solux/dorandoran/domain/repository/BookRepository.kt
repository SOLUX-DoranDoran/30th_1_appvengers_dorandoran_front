package com.solux.dorandoran.domain.repository


import com.solux.dorandoran.domain.entity.BookEntity

interface BookRepository {
    suspend fun getBookInfo(bookId: Long): Result<BookEntity>
}
