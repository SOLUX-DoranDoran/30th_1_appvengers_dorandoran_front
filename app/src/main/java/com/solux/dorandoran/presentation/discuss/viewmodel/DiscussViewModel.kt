package com.solux.dorandoran.presentation.discuss.viewmodel

import androidx.lifecycle.ViewModel
import com.solux.dorandoran.domain.entity.DiscussPageEntity
import com.solux.dorandoran.domain.entity.BookEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.repository.DiscussRepository
import kotlinx.coroutines.launch

@HiltViewModel
class DiscussViewModel @Inject constructor(
    private val discussRepository: DiscussRepository
) : ViewModel() {

    private val _discussions = mutableStateOf<List<DiscussPageEntity>>(emptyList())
    val discussions: State<List<DiscussPageEntity>> = _discussions

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _selectedDiscussion = mutableStateOf<DiscussPageEntity?>(null)
    val selectedDiscussion: State<DiscussPageEntity?> = _selectedDiscussion

    private val _bookDiscussions = mutableStateOf<List<DiscussPageEntity>>(emptyList())
    val bookDiscussions: State<List<DiscussPageEntity>> = _bookDiscussions

    private val _selectedTabIndex = mutableIntStateOf(0) // 0: 토론 목록, 1: 토론 생성
    val selectedTabIndex: State<Int> = _selectedTabIndex

    private val _discussionTopic = mutableStateOf("")
    val discussionTopic: State<String> = _discussionTopic

    private val _argumentContent = mutableStateOf("")
    val argumentContent: State<String> = _argumentContent

    private val _selectedBookTitle = mutableStateOf<String?>(null)
    val selectedBookTitle: State<String?> = _selectedBookTitle

    private val pageSize = 10

    // 수정: 임시 책 정보 더미 데이터 추가
    private val dummyBookInfos = mapOf(
        1 to BookEntity(
            id = 1,
            title = "나미야 잡화점의 기적",
            author = "히가시노 게이고",
            publisher = "현대문학",
            publisherDate = "2012-12-19",
            coverImageUrl = "https://example.com/book1.jpg"
        ),
        2 to BookEntity(
            id = 2,
            title = "미드나잇 라이브러리",
            author = "매트 헤이그",
            publisher = "인플루엔셜",
            publisherDate = "2021-03-03",
            coverImageUrl = "https://example.com/book2.jpg"
        ),
        3 to BookEntity(
            id = 3,
            title = "파친코",
            author = "이민진",
            publisher = "문학사상",
            publisherDate = "2017-11-24",
            coverImageUrl = "https://example.com/book3.jpg"
        )
    )

    init {
        loadDiscussions()
    }

    fun loadDiscussions() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            discussRepository.getDiscussions(
                page = 0,
                size = pageSize
            ).fold(
                onSuccess = { discussions ->
                    _discussions.value = discussions
                },
                onFailure = { exception ->
                    _errorMessage.value = exception.message ?: "토론 목록을 불러오는데 실패했습니다."
                }
            )

            _isLoading.value = false
        }
    }

    fun refreshDiscussions() {
        loadDiscussions()
    }

    fun onDiscussionClicked(discussion: DiscussPageEntity) {
        _selectedDiscussion.value = discussion
    }

    fun selectDiscussion(discussion: DiscussPageEntity) {
        _selectedDiscussion.value = discussion
        loadDiscussionsForBook(discussion.bookTitle)
    }

    fun loadDiscussionsForBook(bookTitle: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val filteredDiscussions = _discussions.value.filter {
                it.bookTitle == bookTitle
            }
            _bookDiscussions.value = filteredDiscussions

            _isLoading.value = false
        }
    }

    fun updateSelectedTab(index: Int) {
        _selectedTabIndex.intValue = index
    }

    fun onDiscussionTopicChange(newTopic: String) {
        _discussionTopic.value = newTopic
    }

    fun onArgumentContentChange(newContent: String) {
        _argumentContent.value = newContent
    }

    fun selectBookForDiscussion(bookTitle: String) {
        _selectedBookTitle.value = bookTitle
    }

    // 수정: createDiscussion 메서드 오버로드 추가
    fun createDiscussion(): Boolean {
        val topic = _discussionTopic.value
        val content = _argumentContent.value

        if (topic.isBlank() || content.isBlank()) {
            _errorMessage.value = "토론 주제와 내용을 모두 입력해주세요."
            return false
        }

        clearDiscussionForm()
        return true
    }

    fun createDiscussion(bookId: Int): Boolean {
        val topic = _discussionTopic.value
        val content = _argumentContent.value

        if (topic.isBlank() || content.isBlank()) {
            _errorMessage.value = "토론 주제와 내용을 모두 입력해주세요."
            return false
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                discussRepository.createDiscussion(bookId.toString(), topic, content)
                    .fold(
                        onSuccess = {
                            clearDiscussionForm()
                            loadDiscussions()
                            _selectedTabIndex.intValue = 0 // 목록 탭으로 이동
                        },
                        onFailure = { exception ->
                            _errorMessage.value = exception.message ?: "토론 생성에 실패했습니다."
                        }
                    )
            } catch (e: Exception) {
                _errorMessage.value = "토론 생성에 실패했습니다."
            }

            _isLoading.value = false
        }
        return true
    }

    private fun clearDiscussionForm() {
        _discussionTopic.value = ""
        _argumentContent.value = ""
        _selectedBookTitle.value = null
    }

    fun getDiscussionById(discussionId: Int): DiscussPageEntity? {
        return _discussions.value.find { it.boardId == discussionId }
    }

    fun getBookInfoByBookId(bookId: Int): BookEntity? {
        return dummyBookInfos[bookId]
    }

    fun getDiscussionsForBook(bookTitle: String): List<DiscussPageEntity> {
        return _discussions.value.filter { it.bookTitle == bookTitle }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}