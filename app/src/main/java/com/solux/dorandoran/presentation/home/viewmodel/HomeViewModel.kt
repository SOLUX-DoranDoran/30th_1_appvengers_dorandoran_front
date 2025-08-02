package com.solux.dorandoran.presentation.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.DiscussionEntity
import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.entity.RecommendedBookEntity
import com.solux.dorandoran.domain.entity.ReviewListEntity
import com.solux.dorandoran.domain.repository.DiscussionRepository
import com.solux.dorandoran.domain.repository.QuoteRepository
import com.solux.dorandoran.domain.repository.RecommendedBookRepository
import com.solux.dorandoran.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recommendedBookRepository: RecommendedBookRepository,
    private val reviewRepository: ReviewRepository,
    private val discussionRepository: DiscussionRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _recommendedBooks = mutableStateOf<List<RecommendedBookEntity>>(emptyList())
    val recommendedBooks: State<List<RecommendedBookEntity>> = _recommendedBooks

    private val _recentReviewList = mutableStateOf<ReviewListEntity?>(null)
    val recentReviewList: State<ReviewListEntity?> = _recentReviewList

    private val _hotDiscussions = mutableStateOf<DiscussionEntity?>(null)
    val hotDiscussions: State<DiscussionEntity?> = _hotDiscussions

    private val _recentEmotionShare = mutableStateOf<QuoteEntity?>(null)
    val recentEmotionShare: State<QuoteEntity?> = _recentEmotionShare

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        loadAllData()
    }

    fun refreshHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val recentReviewDeferred = async { getRecentReviewData() }
                val recentEmotionShareDeferred = async { getRecentEmotionShareData() }
                val recentDiscussionDeferred = async { getRecentDiscussionData() }

                recentReviewDeferred.await()
                recentEmotionShareDeferred.await()
                recentDiscussionDeferred.await()
            } catch (e: Exception) {
                _errorMessage.value = "데이터 새로고침 중 오류가 발생했습니다: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadAllData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val recommendedBooksDeferred = async { getRecommendedBooksData() }
                val recentReviewDeferred = async { getRecentReviewData() }
                val recentEmotionShareDeferred = async { getRecentEmotionShareData() }
                val recentDiscussionDeferred = async { getRecentDiscussionData() }

                recommendedBooksDeferred.await()
                recentReviewDeferred.await()
                recentEmotionShareDeferred.await()
                recentDiscussionDeferred.await()

            } catch (e: Exception) {
                _errorMessage.value = "데이터 로딩 중 오류가 발생했습니다: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun getRecommendedBooksData() {
        try {
            recommendedBookRepository.getRecommendedBooks()
                .onSuccess { books ->
                    _recommendedBooks.value = books
                }
                .onFailure { error ->
                }
        } catch (e: Exception) {
            println("ERROR: 추천 도서 로딩 중 예외 - ${e.message}")
        }
    }

    private suspend fun getRecentReviewData() {
        try {
            val result = reviewRepository.getRecentReviews(
                sort = "recent",
                page = 1,
                size = 1
            )

            result
                .onSuccess { reviewList ->
                    _recentReviewList.value = reviewList.firstOrNull()
                }
                .onFailure { error ->
                    _recentReviewList.value = null
                }
        } catch (e: Exception) {
            _recentReviewList.value = null
        }
    }

    private suspend fun getRecentDiscussionData() {
        try {
            val result = discussionRepository.getRecentDiscussion()

            result
                .onSuccess { discussion ->
                    if (discussion != null) {
                        _hotDiscussions.value = discussion
                    } else {
                        _hotDiscussions.value = null
                    }
                }
                .onFailure { error ->
                    _hotDiscussions.value = null
                }
        } catch (e: Exception) {
            println("ERROR: 최근 토론 중 예외 - ${e.message}")
            _hotDiscussions.value = null
        }
    }

    private suspend fun getRecentEmotionShareData() {
        try {
            val result = quoteRepository.getRecentQuote()

            result
                .onSuccess { quote ->
                    _recentEmotionShare.value = quote
                }
                .onFailure { error ->
                    _recentEmotionShare.value = null
                }
        } catch (e: Exception) {
            println("ERROR: 감성 공유 로딩 중 예외 - ${e.message}")
            _recentEmotionShare.value = null
        }
    }
}