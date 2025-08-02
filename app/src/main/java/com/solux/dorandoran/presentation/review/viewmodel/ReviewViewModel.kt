package com.solux.dorandoran.presentation.review.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.entity.BookReviewsResponseEntity
import com.solux.dorandoran.domain.entity.ReviewDetailEntity
import com.solux.dorandoran.domain.repository.BookRepository
import com.solux.dorandoran.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _selectedTabIndex = mutableIntStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    private val _currentBook = mutableStateOf<BookEntity?>(null)
    val currentBook: State<BookEntity?> = _currentBook

    private val _bookReviews = mutableStateOf<BookReviewsResponseEntity?>(null)
    val bookReviews: State<BookReviewsResponseEntity?> = _bookReviews

    private val _reviewDetails = mutableStateOf<Map<Long, ReviewDetailEntity>>(emptyMap())
    val reviewDetails: State<Map<Long, ReviewDetailEntity>> = _reviewDetails

    private val _newReviewRating = mutableIntStateOf(0)
    val newReviewRating: State<Int> = _newReviewRating

    private val _newReviewContent = mutableStateOf("")
    val newReviewContent: State<String> = _newReviewContent

    private val _commentInputText = mutableStateOf("")
    val commentInputText: State<String> = _commentInputText

    private val _selectedReviewForComment = mutableStateOf<Long?>(null)
    val selectedReviewForComment: State<Long?> = _selectedReviewForComment

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private var currentBookId: Long = 1L

    fun initializeWithBookId(bookId: Long) {
        currentBookId = bookId
        loadBookInfo(bookId)
        loadBookReviews(bookId)
    }

    private fun loadBookInfo(bookId: Long) {
        viewModelScope.launch {
            bookRepository.getBookInfo(bookId)
                .onSuccess { book ->
                    _currentBook.value = book
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "책 정보를 불러오는데 실패했습니다."
                }
        }
    }

    private fun loadBookReviews(bookId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            reviewRepository.getBookReviews(bookId = bookId)
                .onSuccess { bookReviewsResponse ->
                    _bookReviews.value = bookReviewsResponse

                    val reviewDetailsMap = bookReviewsResponse.reviews.associate { review ->
                        review.reviewId to ReviewDetailEntity(
                            id = review.reviewId,
                            bookTitle = review.bookTitle,
                            coverImageUrl = review.coverImageUrl,
                            content = review.content,
                            rating = review.rating,
                            createdAt = review.createdAt,
                            nickname = review.nickname,
                            profileImage = review.profileImage,
                            isLiked = false,
                            likeCount = 0,
                            commentCount = 0,
                            comments = emptyList(),
                            isCommentsVisible = false
                        )
                    }
                    _reviewDetails.value = reviewDetailsMap

                    loadCommentsForReviews(bookReviewsResponse.reviews.map { it.reviewId })
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "리뷰를 불러오는데 실패했습니다."
                }

            _isLoading.value = false
        }
    }

    private fun loadCommentsForReviews(reviewIds: List<Long>) {
        reviewIds.forEach { reviewId ->
            loadReviewComments(reviewId)
        }
    }

    private fun loadReviewComments(reviewId: Long) {
        viewModelScope.launch {
            reviewRepository.getReviewComments(reviewId = reviewId)
                .onSuccess { commentsResponse ->
                    val currentDetail = _reviewDetails.value[reviewId] ?: return@onSuccess
                    val updatedDetail = currentDetail.copy(
                        commentCount = commentsResponse.totalComments,
                        comments = commentsResponse.comments
                    )
                    _reviewDetails.value = _reviewDetails.value + (reviewId to updatedDetail)
                }
                .onFailure { error -> }

        }
    }

    fun updateSelectedTab(index: Int) {
        _selectedTabIndex.intValue = index
    }

    fun updateNewReviewRating(rating: Int) {
        _newReviewRating.intValue = rating
    }

    fun updateNewReviewContent(content: String) {
        _newReviewContent.value = content
    }

    fun submitReview() {
        if (_newReviewRating.intValue > 0 && _newReviewContent.value.isNotBlank()) {
            viewModelScope.launch {
                _isLoading.value = true
                reviewRepository.createReview(
                    bookId = currentBookId,
                    content = _newReviewContent.value,
                    rating = _newReviewRating.intValue
                )
                    .onSuccess { response ->
                        loadBookReviews(currentBookId)

                        _newReviewRating.intValue = 0
                        _newReviewContent.value = ""
                        _selectedTabIndex.intValue = 0
                    }
                    .onFailure { error ->
                        _errorMessage.value = error.message ?: "리뷰 작성에 실패했습니다."
                    }

                _isLoading.value = false
            }
        }
    }

    fun toggleLike(reviewId: Long) {
        viewModelScope.launch {
            reviewRepository.toggleReviewLike(reviewId = reviewId)
                .onSuccess { likeResponse ->
                    val currentDetail = _reviewDetails.value[reviewId] ?: return@onSuccess
                    val updatedDetail = currentDetail.copy(
                        isLiked = likeResponse.success,
                        likeCount = likeResponse.likeCount
                    )
                    _reviewDetails.value = _reviewDetails.value + (reviewId to updatedDetail)
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "좋아요 처리에 실패했습니다."
                }
        }
    }

    fun updateCommentInput(text: String) {
        _commentInputText.value = text
    }

    fun selectReviewForComment(reviewId: Long?) {
        _selectedReviewForComment.value = reviewId
    }

    fun submitComment() {
        val selectedReviewId = _selectedReviewForComment.value ?: return
        val commentText = _commentInputText.value

        if (commentText.isNotBlank()) {
            viewModelScope.launch {
                _isLoading.value = true

                reviewRepository.createReviewComment(
                    reviewId = selectedReviewId,
                    content = commentText
                )
                    .onSuccess { response ->

                        loadReviewComments(selectedReviewId)

                        val currentDetail = _reviewDetails.value[selectedReviewId]
                        if (currentDetail != null) {
                            val updatedDetail = currentDetail.copy(
                                isCommentsVisible = true
                            )
                            _reviewDetails.value = _reviewDetails.value + (selectedReviewId to updatedDetail)
                        }

                        _commentInputText.value = ""
                        _selectedReviewForComment.value = null
                    }
                    .onFailure { error ->
                        _errorMessage.value = error.message ?: "댓글 작성에 실패했습니다."
                    }

                _isLoading.value = false
            }
        }
    }

    fun toggleCommentsVisibility(reviewId: Long) {
        val currentDetail = _reviewDetails.value[reviewId] ?: return
        val updatedDetail = currentDetail.copy(
            isCommentsVisible = !currentDetail.isCommentsVisible
        )

        _reviewDetails.value = _reviewDetails.value + (reviewId to updatedDetail)
    }

    fun getReviewsWithDetails(): List<ReviewDetailEntity> {
        val reviews = _bookReviews.value?.reviews ?: emptyList()
        return reviews.mapNotNull { review ->
            _reviewDetails.value[review.reviewId]
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun refreshReviews() {
        loadBookReviews(currentBookId)
    }
}