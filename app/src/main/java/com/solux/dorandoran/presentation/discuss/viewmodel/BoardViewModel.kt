package com.solux.dorandoran.presentation.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solux.dorandoran.domain.entity.BoardEntity
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.entity.PagedBoardsEntity
import com.solux.dorandoran.domain.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val boardRepository: BoardRepository
) : ViewModel() {

    companion object {
        private const val TAG = "BoardViewModel"
    }

    // ================= 상태 관리 =================

    // 전체 토론 목록 관련
    private val _boards = mutableStateOf<List<BoardEntity>>(emptyList())
    val boards: State<List<BoardEntity>> = _boards

    private val _pagedBoardsInfo = mutableStateOf<PagedBoardsEntity?>(null)
    val pagedBoardsInfo: State<PagedBoardsEntity?> = _pagedBoardsInfo

    // 도서별 토론 목록 관련
    private val _bookBoards = mutableStateOf<List<BoardEntity>>(emptyList())
    val bookBoards: State<List<BoardEntity>> = _bookBoards

    private val _pagedBookBoardsInfo = mutableStateOf<PagedBoardsEntity?>(null)
    val pagedBookBoardsInfo: State<PagedBoardsEntity?> = _pagedBookBoardsInfo

    // 토론 상세 관련
    private val _selectedBoard = mutableStateOf<BoardEntity?>(null)
    val selectedBoard: State<BoardEntity?> = _selectedBoard

    // 도서 정보 관련
    private val _selectedBook = mutableStateOf<BookEntity?>(null)
    val selectedBook: State<BookEntity?> = _selectedBook

    // 토론 생성 관련
    private val _createBoardTitle = mutableStateOf("")
    val createBoardTitle: State<String> = _createBoardTitle

    private val _createBoardContent = mutableStateOf("")
    val createBoardContent: State<String> = _createBoardContent

    private val _createBoardBookTitle = mutableStateOf("")
    val createBoardBookTitle: State<String> = _createBoardBookTitle

    // 공통 상태
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    // ================= 전체 토론 목록 관련 메서드 =================

    fun loadBoards(page: Int = 1, size: Int = 10) {
        Log.d(TAG, "전체 토론 목록 로드 시작 - page: $page, size: $size")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                boardRepository.getBoards(page, size)
                    .onSuccess { pagedBoards ->
                        Log.d(TAG, "전체 토론 목록 로드 성공: ${pagedBoards.content.size}개")
                        _boards.value = pagedBoards.content
                        _pagedBoardsInfo.value = pagedBoards
                        _errorMessage.value = null
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "전체 토론 목록 로드 실패", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "전체 토론 목록 로드 중 예상치 못한 오류", e)
                _errorMessage.value = "예상치 못한 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshBoards() {
        Log.d(TAG, "전체 토론 목록 새로고침")
        loadBoards(page = 1)
    }

    // ================= 도서별 토론 목록 관련 메서드 =================

    fun loadBookBoards(bookId: Int, page: Int = 1, size: Int = 10) {
        Log.d(TAG, "도서별 토론 목록 로드 시작 - bookId: $bookId, page: $page, size: $size")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                boardRepository.getBookBoards(bookId, page, size)
                    .onSuccess { pagedBoards ->
                        Log.d(TAG, "도서별 토론 목록 로드 성공: ${pagedBoards.content.size}개")
                        _bookBoards.value = pagedBoards.content
                        _pagedBookBoardsInfo.value = pagedBoards
                        _errorMessage.value = null
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "도서별 토론 목록 로드 실패 - bookId: $bookId", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "도서별 토론 목록 로드 중 예상치 못한 오류", e)
                _errorMessage.value = "예상치 못한 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ================= 토론 상세 조회 관련 메서드 =================

    fun loadBoardDetail(boardId: Int) {
        Log.d(TAG, "토론 상세 조회 시작 - boardId: $boardId")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                boardRepository.getBoardDetail(boardId)
                    .onSuccess { board ->
                        Log.d(TAG, "토론 상세 조회 성공: ${board.title}")
                        _selectedBoard.value = board
                        _errorMessage.value = null

                        // 수정: 토론 상세 조회 시 해당 도서 정보도 함께 로드
                        loadBookDetail(board.bookId)
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "토론 상세 조회 실패 - boardId: $boardId", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "토론 상세 조회 중 예상치 못한 오류", e)
                _errorMessage.value = "예상치 못한 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ================= 도서 정보 조회 관련 메서드 =================

    fun loadBookDetail(bookId: Int) {
        Log.d(TAG, "도서 정보 조회 시작 - bookId: $bookId")

        viewModelScope.launch {
            try {
                boardRepository.getBookDetail(bookId)
                    .onSuccess { book ->
                        Log.d(TAG, "도서 정보 조회 성공: ${book.title}")
                        _selectedBook.value = book
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "도서 정보 조회 실패 - bookId: $bookId", exception)
                        // 도서 정보 조회 실패는 치명적이지 않으므로 별도 에러 처리
                    }
            } catch (e: Exception) {
                Log.e(TAG, "도서 정보 조회 중 예상치 못한 오류", e)
            }
        }
    }

    // ================= 토론 생성 관련 메서드 =================

    fun updateCreateBoardTitle(title: String) {
        Log.d(TAG, "토론 제목 입력: ${title.take(20)}...")
        _createBoardTitle.value = title
    }

    fun updateCreateBoardContent(content: String) {
        Log.d(TAG, "토론 내용 입력: ${content.take(50)}...")
        _createBoardContent.value = content
    }

    fun updateCreateBoardBookTitle(bookTitle: String) {
        Log.d(TAG, "토론 도서 제목 입력: $bookTitle")
        _createBoardBookTitle.value = bookTitle
    }

    fun createBoard() {
        val title = _createBoardTitle.value.trim()
        val content = _createBoardContent.value.trim()
        val bookTitle = _createBoardBookTitle.value.trim()

        if (title.isEmpty() || content.isEmpty() || bookTitle.isEmpty()) {
            val errorMsg = "제목, 내용, 도서 제목을 모두 입력해주세요."
            Log.w(TAG, errorMsg)
            _errorMessage.value = errorMsg
            return
        }

        Log.d(TAG, "토론 생성 시작 - title: $title, bookTitle: $bookTitle")

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                boardRepository.createBoard(title, content, bookTitle)
                    .onSuccess { createdBoard ->
                        Log.d(TAG, "토론 생성 성공 - boardId: ${createdBoard.id}")

                        // 수정: 생성 성공 후 폼 초기화 및 목록 새로고침
                        clearCreateBoardForm()
                        refreshBoards()
                        _errorMessage.value = null
                    }
                    .onFailure { exception ->
                        Log.e(TAG, "토론 생성 실패", exception)
                        _errorMessage.value = getErrorMessage(exception)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "토론 생성 중 예상치 못한 오류", e)
                _errorMessage.value = "예상치 못한 오류가 발생했습니다."
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun clearCreateBoardForm() {
        Log.d(TAG, "토론 생성 폼 초기화")
        _createBoardTitle.value = ""
        _createBoardContent.value = ""
        _createBoardBookTitle.value = ""
    }

    // ================= 유틸리티 메서드 =================

    fun getBoardById(boardId: Int): BoardEntity? {
        val board = _boards.value.find { it.id == boardId }
            ?: _bookBoards.value.find { it.id == boardId }
        Log.d(TAG, "ID로 토론 검색: $boardId, 결과: ${board?.title ?: "없음"}")
        return board
    }

    fun clearError() {
        Log.d(TAG, "에러 메시지 초기화")
        _errorMessage.value = null
    }

    private fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is HttpException -> {
                when (exception.code()) {
                    401 -> "인증이 필요합니다. 다시 로그인해주세요."
                    403 -> "접근 권한이 없습니다."
                    404 -> "요청한 데이터를 찾을 수 없습니다."
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
        Log.d(TAG, "BoardViewModel 정리됨")
    }
}