package com.solux.dorandoran.presentation.discuss.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.repository.DiscussCommentRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscussCommentViewModel @Inject constructor(
    private val discussCommentRepository: DiscussCommentRepository
) : ViewModel() {

    companion object {
        private const val TAG = "DiscussCommentViewModel"
    }

    // UI 상태 관리
    private val _uiState = mutableStateOf<CommentUiState>(CommentUiState.Loading)
    val uiState: State<CommentUiState> get() = _uiState

    // 댓글 입력 텍스트
    private val _commentInput = mutableStateOf("")
    val commentInput: State<String> = _commentInput

    // 대댓글 대상 (null이면 원댓글, 값이 있으면 대댓글)
    private val _replyTargetId = mutableStateOf<Int?>(null)
    val replyTargetId: State<Int?> = _replyTargetId

    // 댓글 입력창 활성화 상태 (댓글ID별로 관리)
    private val _activeCommentInputMap = mutableStateOf<Map<Int, Boolean>>(emptyMap())
    val activeCommentInputMap: State<Map<Int, Boolean>> = _activeCommentInputMap

    // 로딩 상태
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // 에러 메시지
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    // 수정: 더미 데이터 (서버 연결 실패 시 사용)
    private val dummyComments = listOf(
        DiscussCommentEntity(
            id = 1,
            memberNickname = "토론자1",
            content = "이 책에서 가장 인상 깊었던 부분은 주인공의 내적 갈등이었습니다. 현실과 이상 사이에서 고민하는 모습이 너무 현실적이어서 공감이 많이 되었어요.",
            createdAt = "2024-01-01T10:00:00",
            parentId = null
        ),
        DiscussCommentEntity(
            id = 2,
            memberNickname = "독서왕",
            content = "저는 반대 의견인데요, 주인공의 선택이 너무 이상적이라고 생각합니다. 실제 상황에서는 그렇게 행동하기 어려울 것 같아요.",
            createdAt = "2024-01-01T14:30:00",
            parentId = null
        ),
        DiscussCommentEntity(
            id = 3,
            memberNickname = "책벌레",
            content = "맞아요! 저도 그 부분에서 많은 생각을 하게 되었습니다.",
            createdAt = "2024-01-01T15:45:00",
            parentId = 1
        ),
        DiscussCommentEntity(
            id = 4,
            memberNickname = "문학소녀",
            content = "하지만 그래서 더 의미가 있는 것 같아요. 이상을 추구하는 것도 중요하니까요.",
            createdAt = "2024-01-01T16:20:00",
            parentId = 2
        )
    )

    // 댓글 입력 텍스트 업데이트
    fun updateCommentInput(input: String) {
        Log.d(TAG, "댓글 입력 업데이트: ${input.take(50)}...")
        _commentInput.value = input
    }

    // 댓글 목록 로드
    fun loadComments(boardId: Int) {
        Log.d(TAG, "댓글 목록 로드 시작 - boardId: $boardId")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                _uiState.value = CommentUiState.Loading

                Log.d(TAG, "Repository 호출 시작")
                discussCommentRepository.getDiscussComments(boardId)
                    .onSuccess { comments ->
                        Log.d(TAG, "서버 댓글 로드 성공: ${comments.size}개")
                        _uiState.value = CommentUiState.Success(comments)

                        comments.forEachIndexed { index, comment ->
                            Log.d(TAG, "Comment $index: ID=${comment.id}, parentId=${comment.parentId}")
                        }
                    }
                    .onFailure { exception ->
                        Log.w(TAG, "서버 댓글 로드 실패, 더미 데이터 사용", exception)
                        // 수정: 실패 시 더미 데이터 사용
                        _uiState.value = CommentUiState.Success(dummyComments)
                        Log.d(TAG, "더미 데이터 사용: ${dummyComments.size}개")
                    }
            } catch (e: Exception) {
                Log.e(TAG, "예상치 못한 오류, 더미 데이터 사용", e)
                _uiState.value = CommentUiState.Success(dummyComments)
            } finally {
                _isLoading.value = false
            }
        }
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
                        val currentComments = when (val state = _uiState.value) {
                            is CommentUiState.Success -> state.comments
                            else -> emptyList()
                        }
                        _uiState.value = CommentUiState.Success(currentComments + newComment)

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
                        val currentComments = when (val state = _uiState.value) {
                            is CommentUiState.Success -> state.comments
                            else -> emptyList()
                        }
                        _uiState.value = CommentUiState.Success(currentComments + newComment)

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
        return when (val state = _uiState.value) {
            is CommentUiState.Success -> {
                val mainComments = state.comments.filter { it.parentId == null }
                Log.d(TAG, "원댓글 개수: ${mainComments.size}")
                mainComments
            }
            else -> {
                Log.d(TAG, "UI 상태가 Success가 아님, 빈 목록 반환")
                emptyList()
            }
        }
    }

    // 특정 댓글의 대댓글들 가져오기
    fun getRepliesForComment(parentId: Int): List<DiscussCommentEntity> {
        return when (val state = _uiState.value) {
            is CommentUiState.Success -> {
                val replies = state.comments.filter { it.parentId == parentId }
                Log.d(TAG, "댓글 $parentId 의 대댓글 개수: ${replies.size}")
                replies
            }
            else -> {
                Log.d(TAG, "UI 상태가 Success가 아님, 빈 대댓글 목록 반환")
                emptyList()
            }
        }
    }

    // 정렬된 댓글 목록 반환 (원댓글 → 대댓글 순서)
    fun getOrganizedComments(): List<DiscussCommentEntity> {
        return when (val state = _uiState.value) {
            is CommentUiState.Success -> {
                val allComments = state.comments
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

                Log.d(TAG, "정렬된 댓글 목록 크기: ${organizedList.size}")
                organizedList
            }
            else -> {
                Log.d(TAG, "UI 상태가 Success가 아님, 빈 정렬 목록 반환")
                emptyList()
            }
        }
    }

    // 에러 메시지 클리어
    fun clearError() {
        Log.d(TAG, "에러 메시지 클리어")
        _errorMessage.value = null
    }

    // 댓글 새로고침
    fun refreshComments(boardId: Int) {
        Log.d(TAG, "댓글 새로고침")
        loadComments(boardId)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel 정리됨")
    }
}

// UI 상태 관리를 위한 sealed interface
sealed interface CommentUiState {
    object Loading : CommentUiState
    data class Success(val comments: List<DiscussCommentEntity>) : CommentUiState
    data class Error(val message: String) : CommentUiState
}