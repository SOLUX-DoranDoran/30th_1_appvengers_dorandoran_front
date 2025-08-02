package com.solux.dorandoran.presentation.mypage.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmotionShareViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _quoteList = mutableStateOf<List<QuoteEntity>>(emptyList())
    val quoteList: State<List<QuoteEntity>> = _quoteList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        fetchQuotes()
    }

    fun fetchQuotes(
        sort: String = "recent",
        page: Int = 1,
        size: Int = 10
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = quoteRepository.getQuotes(sort, page, size)

            result.onSuccess { quotes ->
                _quoteList.value = quotes
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "명언을 불러오는데 실패했습니다."
            }

            _isLoading.value = false
        }
    }

    fun postQuote(content: String, bookId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = quoteRepository.postQuote(content, bookId)

            result.onSuccess { quoteId ->
                fetchQuotes()
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "명언 등록에 실패했습니다."
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(quoteId: Long) {
        viewModelScope.launch {
            _errorMessage.value = null

            val currentQuotes = _quoteList.value.toMutableList()
            val quoteIndex = currentQuotes.indexOfFirst { it.id == quoteId }

            if (quoteIndex != -1) {
                val currentQuote = currentQuotes[quoteIndex]
                val updatedQuote = currentQuote.copy(
                    isLiked = !currentQuote.isLiked,
                    likeCount = if (currentQuote.isLiked) {
                        currentQuote.likeCount - 1
                    } else {
                        currentQuote.likeCount + 1
                    }
                )
                currentQuotes[quoteIndex] = updatedQuote
                _quoteList.value = currentQuotes
            }

            val result = quoteRepository.toggleQuoteLike(quoteId)

            result.onFailure { exception ->
                if (quoteIndex != -1) {
                    val rollbackQuotes = _quoteList.value.toMutableList()
                    val originalQuote = rollbackQuotes[quoteIndex]
                    val rolledBackQuote = originalQuote.copy(
                        isLiked = !originalQuote.isLiked,
                        likeCount = if (originalQuote.isLiked) {
                            originalQuote.likeCount - 1
                        } else {
                            originalQuote.likeCount + 1
                        }
                    )
                    rollbackQuotes[quoteIndex] = rolledBackQuote
                    _quoteList.value = rollbackQuotes
                }
                _errorMessage.value = exception.message ?: "좋아요 처리에 실패했습니다."
            }
        }
    }

    fun refreshQuotes() {
        fetchQuotes()
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}