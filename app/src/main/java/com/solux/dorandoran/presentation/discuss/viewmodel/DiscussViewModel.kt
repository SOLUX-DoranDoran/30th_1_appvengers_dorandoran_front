package com.solux.dorandoran.presentation.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.repository.DiscussCommentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class DiscussViewModel @Inject constructor(
    private val discussCommentRepository: DiscussCommentRepository // 수정: 댓글 Repository만 사용
) : ViewModel() {

    companion object {
        private const val TAG = "DiscussViewModel"
    }

    // ================= 댓글 관련 상태들만 유지 =================

    private val _comments = mutableStateOf<List<DiscussCommentEntity>>(emptyList())
    val comments: State<List<DiscussCommentEntity>> = _comments

    private val _commentInput = mutableStateOf("")
    val commentInput: State<String> = _commentInput

    private val _replyTargetId = mutableStateOf<Int?>(null)
    val replyTargetId: State<Int?> = _replyTargetId

    private val _activeCommentInputMap = mutableStateOf<Map<Int, Boolean>>(emptyMap())
    val activeCommentInputMap: State<Map<Int, Boolean>> = _activeCommentInputMap

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    // ================= 댓글 목록 로드 =================

    fun loadComments(boardId: Int, page: Int = 1, size: Int = 10) {
        Log.d(TAG, "댓글 목록 로드 시작 - boardId: $boardId, page: $page, size: $size")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                discussCommentRepository.getDiscussComments(boardId, page, size)
                    .onSuccess { comments ->
                        Log.d(TAG, "서버 댓글 로드 성공: ${comments.size}개")
                        _comments.value = comments
                        _errorMessage.value = null

                        comments.forEachIndexed { index, comment ->
                            Log.d(TAG, "Comment $index: ID=${comment.id}, parentId=${comment.parentId}")
                        }
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "서버 댓글 로드 실패", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "댓글 로드 중 예상치 못한 오류", e)
                _errorMessage.value = "예상치 못한 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ================= 댓글 입력 관리 =================

    fun updateCommentInput(input: String) {
        Log.d(TAG, "댓글 입력 업데이트: ${input.take(50)}...")
        _commentInput.value = input
    }

    // ================= 댓글 작성 (원댓글) =================

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

                        // 수정: 기존 댓글 목록에 새 댓글 추가
                        _comments.value = _comments.value + newComment

                        // 입력창 초기화
                        _commentInput.value = ""
                        _replyTargetId.value = null
                        _errorMessage.value = null
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "원댓글 작성 실패", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "원댓글 작성 중 예외", e)
                _errorMessage.value = "댓글 작성 중 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ================= 대댓글 작성 =================

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

                        // 수정: 기존 댓글 목록에 새 대댓글 추가
                        _comments.value = _comments.value + newComment

                        // 대댓글 입력창 비활성화
                        toggleCommentInput(parentId)
                        _errorMessage.value = null
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "대댓글 작성 실패", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "대댓글 작성 중 예외", e)
                _errorMessage.value = "댓글 작성 중 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ================= 댓글 입력창 관리 =================

    fun toggleCommentInput(commentId: Int) {
        Log.d(TAG, "댓글 입력창 토글: $commentId")
        val currentMap = _activeCommentInputMap.value.toMutableMap()
        currentMap[commentId] = !(currentMap[commentId] ?: false)
        _activeCommentInputMap.value = currentMap
    }

    fun setReplyTarget(parentId: Int?) {
        Log.d(TAG, "대댓글 대상 설정: $parentId")
        _replyTargetId.value = parentId
    }

    // ================= 댓글 조회 유틸리티 메서드들 =================

    fun getMainComments(): List<DiscussCommentEntity> {
        val mainComments = _comments.value.filter { it.parentId == null }
        Log.d(TAG, "원댓글 개수: ${mainComments.size}")
        return mainComments
    }

    fun getRepliesForComment(parentId: Int): List<DiscussCommentEntity> {
        val replies = _comments.value.filter { it.parentId == parentId }
        Log.d(TAG, "댓글 $parentId 의 대댓글 개수: ${replies.size}")
        return replies
    }

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

        Log.d(TAG, "정렬된 댓글 목록 크기: ${organizedList.size}")
        return organizedList
    }

    // ================= 댓글 새로고침 =================

    fun refreshComments(boardId: Int) {
        Log.d(TAG, "댓글 새로고침")
        loadComments(boardId)
    }

    // ================= 유틸리티 메서드 =================

    fun clearError() {
        Log.d(TAG, "에러 메시지 초기화")
        _errorMessage.value = null
    }

    private fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    401 -> "인증이 필요합니다. 다시 로그인해주세요."
                    403 -> "댓글 작성 권한이 없습니다."
                    404 -> "토론을 찾을 수 없습니다."
                    500 -> "서버 오류가 발생했습니다."
                    else -> "네트워크 오류가 발생했습니다. (${exception.code()})"
                }
            }
            is UnknownHostException -> "인터넷 연결을 확인해주세요."
            is SocketTimeoutException -> "요청 시간이 초과되었습니다. 다시 시도해주세요."
            else -> exception.message ?: "알 수 없는 오류가 발생했습니다."
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "DiscussViewModel 정리됨")
    }
}