package com.solux.dorandoran.presentation.discuss.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface BookUiState {
    object Loading : BookUiState
    data class Success(val book: BookEntity) : BookUiState
    data class Error(val message: String) : BookUiState
}

class BookViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookUiState>(BookUiState.Loading)
    val uiState: StateFlow<BookUiState> = _uiState

    fun fetchBookInfo(bookId: Long) {
        _uiState.value = BookUiState.Loading
        viewModelScope.launch {
            val result = bookRepository.getBookInfo(bookId)
            result.onSuccess { book ->
                _uiState.value = BookUiState.Success(book)
            }.onFailure { throwable ->
                _uiState.value = BookUiState.Error(throwable.message ?: "알 수 없는 오류 발생")
            }
        }
    }
}
