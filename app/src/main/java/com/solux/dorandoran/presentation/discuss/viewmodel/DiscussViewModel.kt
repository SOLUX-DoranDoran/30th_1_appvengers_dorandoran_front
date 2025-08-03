package com.solux.dorandoran.presentation.discuss.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.solux.dorandoran.domain.entity.DiscussPageEntity
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.repository.DiscussRepository
import com.solux.dorandoran.domain.repository.DiscussCommentRepository
import kotlinx.coroutines.launch

@HiltViewModel
class DiscussViewModel @Inject constructor(
    private val discussRepository: DiscussRepository,
    private val discussCommentRepository: DiscussCommentRepository // 수정: 댓글 Repository 추가
) : ViewModel() {

    companion object {
        private const val TAG = "DiscussViewModel"
    }

    // 기존 토론 목록 관련 상태들
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

    private val _selectedTabIndex = mutableIntStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    private val _discussionTopic = mutableStateOf("")
    val discussionTopic: State<String> = _discussionTopic

    private val _argumentContent = mutableStateOf("")
    val argumentContent: State<String> = _argumentContent

    private val _selectedBookTitle = mutableStateOf<String?>(null)
    val selectedBookTitle: State<String?> = _selectedBookTitle

    // 수정: 댓글 관련 상태들 추가
    private val _comments = mutableStateOf<List<DiscussCommentEntity>>(emptyList())
    val comments: State<List<DiscussCommentEntity>> = _comments

    private val _commentInput = mutableStateOf("")
    val commentInput: State<String> = _commentInput

    private val _replyTargetId = mutableStateOf<Int?>(null)
    val replyTargetId: State<Int?> = _replyTargetId

    private val _activeCommentInputMap = mutableStateOf<Map<Int, Boolean>>(emptyMap())
    val activeCommentInputMap: State<Map<Int, Boolean>> = _activeCommentInputMap

    private val pageSize = 10

    // 수정: 더미 데이터로 우선 화면 표시
    private val dummyDiscussions = listOf(
        DiscussPageEntity(
            boardId = 1,
            bookId = 1,
            memberId = 1,
            bookTitle = "나미야 잡화점의 기적",
            content = "시간을 초월한 편지의 의미에 대해 토론해봅시다. 과거와 현재, 미래가 연결되는 이 신비로운 공간에서 벌어지는 이야기들을 통해 우리는 무엇을 배울 수 있을까요?",
            createdAt = "2024-01-01T10:00:00"
        ),
        DiscussPageEntity(
            boardId = 2,
            bookId = 2,
            memberId = 2,
            bookTitle = "미드나잇 라이브러리",
            content = "무한한 가능성의 삶에 대해 어떻게 생각하시나요? 노라가 경험한 다양한 인생들을 통해 우리는 어떤 교훈을 얻을 수 있을까요?",
            createdAt = "2024-01-02T14:30:00"
        ),
        DiscussPageEntity(
            boardId = 3,
            bookId = 3,
            memberId = 3,
            bookTitle = "파친코",
            content = "재일조선인의 삶과 정체성에 대해 이야기해봅시다. 선자와 그 가족들이 겪은 역사적 아픔과 희망을 어떻게 이해해야 할까요?",
            createdAt = "2024-01-03T16:45:00"
        ),
        DiscussPageEntity(
            boardId = 4,
            bookId = 4,
            memberId = 4,
            bookTitle = "데미안",
            content = "헤르만 헤세의 데미안에서 나타나는 자아 찾기의 여정에 대해 토론해보죠. 싱클레어의 성장 과정에서 우리가 배울 점은 무엇일까요?",
            createdAt = "2024-01-04T09:15:00"
        ),
        DiscussPageEntity(
            boardId = 5,
            bookId = 5,
            memberId = 5,
            bookTitle = "달러구트 꿈 백화점",
            content = "꿈을 사고파는 신비로운 백화점의 이야기. 현실과 꿈의 경계에서 벌어지는 따뜻한 이야기들에 대해 어떻게 생각하시나요?",
            createdAt = "2024-01-05T11:20:00"
        )
    )

    // 수정: 더미 댓글 데이터 추가
    private val dummyComments = listOf(
        DiscussCommentEntity(
            id = 1,
            memberNickname = "토론자1",
            content = "이 책에서 가장 인상 깊었던 부분은 주인공의 내적 갈등이었습니다.",
            createdAt = "2024-01-01T10:00:00",
            parentId = null
        ),
        DiscussCommentEntity(
            id = 2,
            memberNickname = "독서왕",
            content = "저는 반대 의견인데요, 주인공의 선택이 너무 이상적이라고 생각합니다.",
            createdAt = "2024-01-01T14:30:00",
            parentId = null
        ),
        DiscussCommentEntity(
            id = 3,
            memberNickname = "책벌레",
            content = "맞아요! 저도 그 부분에서 많은 생각을 하게 되었습니다.",
            createdAt = "2024-01-01T15:45:00",
            parentId = 1
        )
    )

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
        ),
        4 to BookEntity(
            id = 4,
            title = "데미안",
            author = "헤르만 헤세",
            publisher = "민음사",
            publisherDate = "1999-02-20",
            coverImageUrl = "https://example.com/book4.jpg"
        ),
        5 to BookEntity(
            id = 5,
            title = "달러구트 꿈 백화점",
            author = "이미예",
            publisher = "팩토리나인",
            publisherDate = "2020-07-02",
            coverImageUrl = "https://example.com/book5.jpg"
        )
    )

    init {
        Log.d(TAG, "ViewModel 초기화 시작")
        loadDummyDataFirst()
        loadDiscussions()
    }

    // ================= 기존 토론 목록 관련 메서드들 =================

    private fun loadDummyDataFirst() {
        Log.d(TAG, "더미 데이터 우선 로드")
        _discussions.value = dummyDiscussions
        _isLoading.value = false
        _errorMessage.value = null
        Log.d(TAG, "더미 데이터 로드 완료: ${dummyDiscussions.size}개")
    }

    fun loadDiscussions() {
        Log.d(TAG, "서버 데이터 로드 시도 (백그라운드)")

        viewModelScope.launch {
            try {
                Log.d(TAG, "Repository 호출 시작 - page: 0, size: $pageSize")

                discussRepository.getDiscussions(
                    page = 0,
                    size = pageSize
                ).fold(
                    onSuccess = { discussions ->
                        Log.d(TAG, "서버 데이터 로드 성공: ${discussions.size}개")
                        _discussions.value = discussions
                        _errorMessage.value = null

                        discussions.forEachIndexed { index, discussion ->
                            Log.d(TAG, "Server Discussion $index: ID=${discussion.boardId}, Title=${discussion.bookTitle}")
                        }
                    },
                    onFailure = { exception ->
                        Log.w(TAG, "서버 데이터 로드 실패, 더미 데이터 유지", exception)
                        Log.d(TAG, "더미 데이터로 계속 진행: ${_discussions.value.size}개")
                    }
                )
            } catch (e: Exception) {
                Log.w(TAG, "예상치 못한 오류, 더미 데이터 유지", e)
                Log.d(TAG, "더미 데이터로 계속 진행: ${_discussions.value.size}개")
            }
        }
    }

    fun refreshDiscussions() {
        Log.d(TAG, "토론 목록 새로고침")
        loadDiscussions()
    }

    fun onDiscussionClicked(discussion: DiscussPageEntity) {
        Log.d(TAG, "토론 클릭: ${discussion.boardId}")
        _selectedDiscussion.value = discussion
    }

    fun selectDiscussion(discussion: DiscussPageEntity) {
        Log.d(TAG, "토론 선택: ${discussion.boardId}")
        _selectedDiscussion.value = discussion
        loadDiscussionsForBook(discussion.bookTitle)
    }

    fun loadDiscussionsForBook(bookTitle: String) {
        Log.d(TAG, "책별 토론 로드: $bookTitle")
        viewModelScope.launch {
            val filteredDiscussions = _discussions.value.filter {
                it.bookTitle == bookTitle
            }
            Log.d(TAG, "필터링된 토론 수: ${filteredDiscussions.size}")
            _bookDiscussions.value = filteredDiscussions
        }
    }

    fun updateSelectedTab(index: Int) {
        Log.d(TAG, "탭 변경: $index")
        _selectedTabIndex.intValue = index
    }

    fun onDiscussionTopicChange(newTopic: String) {
        Log.d(TAG, "토론 주제 변경: $newTopic")
        _discussionTopic.value = newTopic
    }

    fun onArgumentContentChange(newContent: String) {
        Log.d(TAG, "토론 내용 변경: ${newContent.take(50)}...")
        _argumentContent.value = newContent
    }

    fun selectBookForDiscussion(bookTitle: String) {
        Log.d(TAG, "토론용 책 선택: $bookTitle")
        _selectedBookTitle.value = bookTitle
    }

    fun createDiscussion(): Boolean {
        Log.d(TAG, "토론 생성 (기본)")
        val topic = _discussionTopic.value
        val content = _argumentContent.value

        if (topic.isBlank() || content.isBlank()) {
            val errorMsg = "토론 주제와 내용을 모두 입력해주세요."
            Log.w(TAG, errorMsg)
            _errorMessage.value = errorMsg
            return false
        }

        Log.d(TAG, "토론 생성 성공")
        clearDiscussionForm()
        return true
    }

    fun createDiscussion(bookId: Int): Boolean {
        Log.d(TAG, "토론 생성 (책 ID: $bookId)")
        val topic = _discussionTopic.value
        val content = _argumentContent.value

        if (topic.isBlank() || content.isBlank()) {
            val errorMsg = "토론 주제와 내용을 모두 입력해주세요."
            Log.w(TAG, errorMsg)
            _errorMessage.value = errorMsg
            return false
        }

        viewModelScope.launch {
            try {
                Log.d(TAG, "Repository 토론 생성 호출")
                discussRepository.createDiscussion(bookId.toString(), topic, content)
                    .fold(
                        onSuccess = {
                            Log.d(TAG, "토론 생성 성공")
                            clearDiscussionForm()
                            loadDiscussions()
                            _selectedTabIndex.intValue = 0
                        },
                        onFailure = { exception ->
                            Log.e(TAG, "토론 생성 실패", exception)
                            _errorMessage.value = exception.message ?: "토론 생성에 실패했습니다."
                        }
                    )
            } catch (e: Exception) {
                Log.e(TAG, "토론 생성 중 예외", e)
                _errorMessage.value = "토론 생성에 실패했습니다."
            }
        }
        return true
    }

    private fun clearDiscussionForm() {
        Log.d(TAG, "토론 폼 초기화")
        _discussionTopic.value = ""
        _argumentContent.value = ""
        _selectedBookTitle.value = null
    }

    // ================= 새로 추가된 댓글 관련 메서드들 =================

    // 댓글 목록 로드
    fun loadComments(boardId: Int) {
        Log.d(TAG, "댓글 목록 로드 시작 - boardId: $boardId")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                Log.d(TAG, "댓글 Repository 호출 시작")
                discussCommentRepository.getDiscussComments(boardId)
                    .onSuccess { comments ->
                        Log.d(TAG, "서버 댓글 로드 성공: ${comments.size}개")
                        _comments.value = comments

                        comments.forEachIndexed { index, comment ->
                            Log.d(TAG, "Comment $index: ID=${comment.id}, parentId=${comment.parentId}")
                        }
                    }
                    .onFailure { exception ->
                        Log.w(TAG, "서버 댓글 로드 실패, 더미 데이터 사용", exception)
                        _comments.value = dummyComments
                        Log.d(TAG, "더미 댓글 데이터 사용: ${dummyComments.size}개")
                    }
            } catch (e: Exception) {
                Log.e(TAG, "댓글 로드 중 예상치 못한 오류, 더미 데이터 사용", e)
                _comments.value = dummyComments
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 댓글 입력 텍스트 업데이트
    fun updateCommentInput(input: String) {
        Log.d(TAG, "댓글 입력 업데이트: ${input.take(50)}...")
        _commentInput.value = input
    }

    // 댓글 작성 (원댓글)
    fun createComment(boardId: Int) {
        val content = _commentInput.value.trim()
        if (content.isEmpty()) {
            Log.w(TAG, "댓글 내용이 비어있음")
            _errorMessage.value = "댓글 내용을 입력해주세요."
            return
        }

        Log.d(TAG, "원댓글 작성 시작 - boardId: $boardId, content: ${content.take(50)}...")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                discussCommentRepository.createDiscussComment(
                    boardId = boardId,
                    content = content,
                    parentId = null // 원댓글이므로 null
                )
                    .onSuccess { newComment ->
                        Log.d(TAG, "원댓글 작성 성공: ${newComment.id}")

                        // 기존 댓글 목록에 새 댓글 추가
                        _comments.value = _comments.value + newComment

                        // 입력창 초기화
                        _commentInput.value = ""
                        _replyTargetId.value = null
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "원댓글 작성 실패", exception)
                        _errorMessage.value = exception.message ?: "댓글 작성에 실패했습니다."
                    }
            } catch (e: Exception) {
                Log.e(TAG, "원댓글 작성 중 예외", e)
                _errorMessage.value = "댓글 작성 중 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 대댓글 작성
    fun createReply(boardId: Int, parentId: Int, content: String) {
        if (content.trim().isEmpty()) {
            Log.w(TAG, "대댓글 내용이 비어있음")
            _errorMessage.value = "댓글 내용을 입력해주세요."
            return
        }

        Log.d(TAG, "대댓글 작성 시작 - boardId: $boardId, parentId: $parentId, content: ${content.take(50)}...")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                discussCommentRepository.createDiscussComment(
                    boardId = boardId,
                    content = content.trim(),
                    parentId = parentId // 대댓글이므로 부모 ID 전달
                )
                    .onSuccess { newComment ->
                        Log.d(TAG, "대댓글 작성 성공: ${newComment.id}, parentId: ${newComment.parentId}")

                        // 기존 댓글 목록에 새 대댓글 추가
                        _comments.value = _comments.value + newComment

                        // 대댓글 입력창 비활성화
                        toggleCommentInput(parentId)
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "대댓글 작성 실패", exception)
                        _errorMessage.value = exception.message ?: "댓글 작성에 실패했습니다."
                    }
            } catch (e: Exception) {
                Log.e(TAG, "대댓글 작성 중 예외", e)
                _errorMessage.value = "댓글 작성 중 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 댓글 입력창 토글 (대댓글 작성 시 사용)
    fun toggleCommentInput(commentId: Int) {
        Log.d(TAG, "댓글 입력창 토글: $commentId")
        val currentMap = _activeCommentInputMap.value.toMutableMap()
        currentMap[commentId] = !(currentMap[commentId] ?: false)
        _activeCommentInputMap.value = currentMap
    }

    // 대댓글 대상 설정
    fun setReplyTarget(parentId: Int?) {
        Log.d(TAG, "대댓글 대상 설정: $parentId")
        _replyTargetId.value = parentId
    }

    // 원댓글들만 가져오기
    fun getMainComments(): List<DiscussCommentEntity> {
        val mainComments = _comments.value.filter { it.parentId == null }
        Log.d(TAG, "원댓글 개수: ${mainComments.size}")
        return mainComments
    }

    // 특정 댓글의 대댓글들 가져오기
    fun getRepliesForComment(parentId: Int): List<DiscussCommentEntity> {
        val replies = _comments.value.filter { it.parentId == parentId }
        Log.d(TAG, "댓글 $parentId 의 대댓글 개수: ${replies.size}")
        return replies
    }

    // 정렬된 댓글 목록 반환 (원댓글 → 대댓글 순서)
    fun getOrganizedComments(): List<DiscussCommentEntity> {
        val allComments = _comments.value
        val parentComments = allComments.filter { it.parentId == null }
        val childComments = allComments.filter { it.parentId != null }

        val organizedList = mutableListOf<DiscussCommentEntity>()

        parentComments.forEach { parent ->
            organizedList.add(parent)
            // 해당 부모 댓글의 대댓글들 추가
            childComments.filter { it.parentId == parent.id }
                .forEach { child ->
                    organizedList.add(child)
                }
        }

        Log.d(TAG, "정렷된 댓글 목록 크기: ${organizedList.size}")
        return organizedList
    }

    // 댓글 새로고침
    fun refreshComments(boardId: Int) {
        Log.d(TAG, "댓글 새로고침")
        loadComments(boardId)
    }

    // ================= 기타 유틸리티 메서드들 =================

    fun getDiscussionById(discussionId: Int): DiscussPageEntity? {
        val discussion = _discussions.value.find { it.boardId == discussionId }
        Log.d(TAG, "ID로 토론 검색: $discussionId, 결과: ${discussion?.bookTitle ?: "없음"}")
        return discussion
    }

    fun getBookInfoByBookId(bookId: Int): BookEntity? {
        val book = dummyBookInfos[bookId]
        Log.d(TAG, "ID로 책 검색: $bookId, 결과: ${book?.title ?: "없음"}")
        return book
    }

    fun getDiscussionsForBook(bookTitle: String): List<DiscussPageEntity> {
        val discussions = _discussions.value.filter { it.bookTitle == bookTitle }
        Log.d(TAG, "책별 토론 검색: $bookTitle, 결과: ${discussions.size}개")
        return discussions
    }

    fun clearError() {
        Log.d(TAG, "에러 메시지 초기화")
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel 정리됨")
    }
}