package com.solux.dorandoran.presentation.review.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.ReviewListEntity
import com.solux.dorandoran.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _recentReviews = mutableStateOf<List<ReviewListEntity>>(emptyList())
    val recentReviews: State<List<ReviewListEntity>> = _recentReviews

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        loadRecentReviews()
    }

    private fun loadRecentReviews() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            reviewRepository.getRecentReviews(
                sort = "recent",
                page = 1,
                size = 20
            )
                .onSuccess { reviews ->
                    _recentReviews.value = reviews
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "리뷰를 불러오는데 실패했습니다."
                    _recentReviews.value = emptyList()
                }

            _isLoading.value = false
        }
    }

    fun refreshReviews() {
        loadRecentReviews()
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}