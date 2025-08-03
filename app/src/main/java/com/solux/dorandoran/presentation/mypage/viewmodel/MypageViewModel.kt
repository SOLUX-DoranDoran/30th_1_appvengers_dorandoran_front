package com.solux.dorandoran.presentation.mypage.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.entity.ReviewListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    // 필요한 Repository들을 주입받습니다
    // private val reviewRepository: ReviewRepository,
    // private val quoteRepository: QuoteRepository,
    // private val userRepository: UserRepository
) : ViewModel() {

    // 탭 선택 상태
    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    // 사용자 프로필 정보
    private val _userProfile = mutableStateOf<UserProfile?>(null)
    val userProfile: State<UserProfile?> = _userProfile

    // 내 리뷰 목록
    private val _myReviews = mutableStateOf<List<ReviewListEntity>>(emptyList())
    val myReviews: State<List<ReviewListEntity>> = _myReviews

    // 내 감성글귀 목록
    private val _myQuotes = mutableStateOf<List<QuoteEntity>>(emptyList())
    val myQuotes: State<List<QuoteEntity>> = _myQuotes

    // 로딩 상태
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // 에러 메시지
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    /**
     * 마이페이지 초기화
     */
    fun initializeMyPage() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 사용자 프로필 정보 로드
                loadUserProfile()

                // 선택된 탭에 따라 데이터 로드
                when (_selectedTabIndex.value) {
                    0 -> loadMyReviews()
                    1 -> loadMyQuotes()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * 탭 선택 업데이트
     */
    fun updateSelectedTab(index: Int) {
        _selectedTabIndex.value = index

        // 탭이 바뀔 때마다 해당 데이터 로드
        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (index) {
                    0 -> loadMyReviews()
                    1 -> loadMyQuotes()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * 사용자 프로필 정보 로드
     */
    private suspend fun loadUserProfile() {
        // TODO: UserRepository를 통해 현재 로그인한 사용자 정보 가져오기
        // val profile = userRepository.getCurrentUserProfile()
        // _userProfile.value = profile

        // 임시 데이터
        _userProfile.value = UserProfile(
            id = 1L,
            nickname = "승이",
            profileImageUrl = null
        )
    }

    /**
     * 내 리뷰 목록 로드
     */
    private suspend fun loadMyReviews() {
        // TODO: ReviewRepository를 통해 현재 사용자의 리뷰 목록 가져오기
        // val reviews = reviewRepository.getMyReviews()
        // _myReviews.value = reviews

        // 임시 데이터
        _myReviews.value = generateSampleReviews()
    }

    /**
     * 내 감성글귀 목록 로드
     */
    private suspend fun loadMyQuotes() {
        // TODO: QuoteRepository를 통해 현재 사용자의 감성글귀 목록 가져오기
        // val quotes = quoteRepository.getMyQuotes()
        // _myQuotes.value = quotes

        // 임시 데이터
        _myQuotes.value = generateSampleQuotes()
    }

    /**
     * 감성글귀 좋아요 토글
     */
    fun toggleQuoteLike(quoteId: Long) {
        viewModelScope.launch {
            try {
                // TODO: QuoteRepository를 통해 좋아요 토글
                // quoteRepository.toggleLike(quoteId)

                // 임시로 로컬 상태 업데이트
                _myQuotes.value = _myQuotes.value.map { quote ->
                    if (quote.id == quoteId) {
                        quote.copy(
                            isLiked = !quote.isLiked,
                            likeCount = if (quote.isLiked) quote.likeCount - 1 else quote.likeCount + 1
                        )
                    } else {
                        quote
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    /**
     * 에러 메시지 클리어
     */
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    // 임시 데이터 생성 함수들
    private fun generateSampleReviews(): List<ReviewListEntity> {
        return listOf(
            ReviewListEntity(
                reviewId = 1L,
                bookId = 1L,
                bookTitle = "소년이 온다",
                coverImageUrl = "https://example.com/book1.jpg",
                rating = 5,
                content = "한강 작가님 최고... 감동적이었어요. 정말 좋은 책이었습니다.",
                nickname = "송이",
                createdAt = "2024-01-15T10:30:00",
                profileImage = ""
            ),
            ReviewListEntity(
                reviewId = 2L,
                bookId = 2L,
                bookTitle = "채식주의자",
                coverImageUrl = "https://example.com/book2.jpg",
                rating = 4,
                content = "생각할 거리가 많은 책이었습니다. 한강 작가님의 문체가 인상적이에요.",
                nickname = "송이",
                createdAt = "2024-01-10T14:20:00",
                profileImage = ""
            )
        )
    }

    private fun generateSampleQuotes(): List<QuoteEntity> {
        return listOf(
            QuoteEntity(
                id = 1L,
                bookId = 1L,
                bookTitle = "소년이 온다",
                content = "우리는 모두 시간의 강을 건너는 나그네일 뿐이다. 그 강물에 발을 담그고 있는 동안, 우리는 서로를 만나고 헤어진다.",
                nickname = "송이",
                isLiked = true,
                likeCount = 12,
                createdAt = "2024-01-15T16:45:00",
                coverImageUrl = "",
                profileImage = ""
            ),
            QuoteEntity(
                id = 2L,
                bookId = 2L,
                bookTitle = "채식주의자",
                content = "꿈은 우리가 깨어있을 때 보는 것이 아니라, 잠들어 있을 때 우리를 깨우는 것이다.",
                nickname = "송이",
                isLiked = false,
                likeCount = 8,
                createdAt = "2024-01-12T11:20:00",
                coverImageUrl = "",
                profileImage = ""
            )
        )
    }
}

// 사용자 프로필 데이터 클래스
data class UserProfile(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?
)