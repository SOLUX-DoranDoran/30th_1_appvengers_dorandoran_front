package com.solux.dorandoran.presentation.discuss.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.repository.ArgumentRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArgumentViewModel @Inject constructor(
    private val argumentRepository: ArgumentRepository
) : ViewModel() {

    private val _uiState = mutableStateOf<ArgumentUiState>(ArgumentUiState.Loading)
    val uiState: State<ArgumentUiState> get() = _uiState

    var argumentInput by mutableStateOf("")
        private set

    // 수정: 댓글 입력 상태 관리 추가
    private val _activeCommentInputMap = mutableStateOf<Map<Int, Boolean>>(emptyMap())
    val activeCommentInputMap: State<Map<Int, Boolean>> = _activeCommentInputMap

    // 수정: 토론별 댓글 데이터 관리
    private val _discussionComments = mutableStateOf<Map<Int, List<DiscussCommentEntity>>>(emptyMap())
    val discussionComments: State<Map<Int, List<DiscussCommentEntity>>> = _discussionComments

    // 수정: 임시 더미 데이터
    private val dummyComments = mutableMapOf<Int, List<DiscussCommentEntity>>()

    fun updateArgumentInput(newInput: String) {
        argumentInput = newInput
    }

    fun getArguments(boardId: Int) {
        _uiState.value = ArgumentUiState.Loading
        viewModelScope.launch {
            val result = argumentRepository.getArgumentsForDiscussion(boardId)
            result.onSuccess { arguments ->
                _uiState.value = ArgumentUiState.Success(arguments)
            }.onFailure { e ->
                _uiState.value = ArgumentUiState.Error(e.message ?: "알 수 없는 오류가 발생했습니다.")
            }
        }
    }

    fun postArgument(boardId: Int) {
        if (argumentInput.isBlank()) return
        viewModelScope.launch {
            val result = argumentRepository.createArgument(boardId, argumentInput)
            result.onSuccess { newArgument ->
                val current = (_uiState.value as? ArgumentUiState.Success)?.arguments ?: emptyList()
                _uiState.value = ArgumentUiState.Success(listOf(newArgument) + current)
                argumentInput = ""
            }
        }
    }

    fun postComment(boardId: Int, parentId: Int, content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            val result = argumentRepository.createComment(boardId, content, parentId)
            result.onSuccess { newComment ->
                val current = (_uiState.value as? ArgumentUiState.Success)?.arguments ?: emptyList()
                _uiState.value = ArgumentUiState.Success(current + newComment)
            }
        }
    }

    // 수정: 댓글 입력 토글 메서드 추가
    fun toggleCommentInput(argumentId: Int) {
        val currentMap = _activeCommentInputMap.value.toMutableMap()
        currentMap[argumentId] = !(currentMap[argumentId] ?: false)
        _activeCommentInputMap.value = currentMap
    }

    // 수정: 토론별 댓글 로드 메서드 추가
    fun loadCommentsForDiscussion(discussionId: Int) {
        // 임시 더미 데이터 생성
        val dummyComments = listOf(
            DiscussCommentEntity(
                id = 1,
                memberNickname = "사용자1",
                content = "이 의견에 동의합니다.",
                createdAt = "2024-01-01",
                parentId = null
            ),
            DiscussCommentEntity(
                id = 2,
                memberNickname = "사용자2",
                content = "반대 의견입니다.",
                createdAt = "2024-01-02",
                parentId = null
            )
        )

        val currentComments = _discussionComments.value.toMutableMap()
        currentComments[discussionId] = dummyComments
        _discussionComments.value = currentComments
    }

    // 수정: 메인 argument들만 가져오는 메서드 추가
    fun getMainArguments(): List<DiscussCommentEntity> {
        return when (val state = _uiState.value) {
            is ArgumentUiState.Success -> state.arguments.filter { it.parentId == null }
            else -> {
                // 임시 더미 데이터 반환
                listOf(
                    DiscussCommentEntity(
                        id = 1,
                        memberNickname = "토론자1",
                        content = "첫 번째 의견입니다.",
                        createdAt = "2024-01-01",
                        parentId = null
                    ),
                    DiscussCommentEntity(
                        id = 2,
                        memberNickname = "토론자2",
                        content = "두 번째 의견입니다.",
                        createdAt = "2024-01-02",
                        parentId = null
                    )
                )
            }
        }
    }

    // 수정: 특정 argument에 대한 댓글들 가져오는 메서드 추가
    fun getCommentsForArgument(argumentId: Int): List<DiscussCommentEntity> {
        return when (val state = _uiState.value) {
            is ArgumentUiState.Success -> state.arguments.filter { it.parentId == argumentId }
            else -> emptyList()
        }
    }
}

sealed interface ArgumentUiState {
    object Loading : ArgumentUiState
    data class Success(val arguments: List<DiscussCommentEntity>) : ArgumentUiState
    data class Error(val message: String) : ArgumentUiState
}