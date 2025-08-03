package com.solux.dorandoran.presentation.discuss.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.component.DiscussionBookBox
import com.solux.dorandoran.core_ui.component.DiscussionRoomBox
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.largeBold
import com.solux.dorandoran.domain.entity.BookEntity
import com.solux.dorandoran.domain.entity.BoardEntity
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.entity.DiscussPageEntity
import com.solux.dorandoran.presentation.discuss.viewmodel.BoardViewModel // 수정: BoardViewModel 사용

@Composable
fun DiscussionRoomRoute(
    navigator: DiscussNavigator,
    discussionId: Int,
    boardViewModel: BoardViewModel = hiltViewModel() // 수정: BoardViewModel 사용
) {
    var showAddDiscussionScreen by remember { mutableStateOf(false) }

    // 수정: BoardViewModel에서 상태들 가져오기
    val selectedBoard by boardViewModel.selectedBoard
    val selectedBook by boardViewModel.selectedBook
    val bookBoards by boardViewModel.bookBoards
    val isLoading by boardViewModel.isLoading
    val errorMessage by boardViewModel.errorMessage

    // 수정: 토론 상세 정보 및 도서별 토론 목록 로드
    LaunchedEffect(discussionId) {
        Log.d("DiscussionRoomRoute", "토론 정보 로드: $discussionId")
        boardViewModel.loadBoardDetail(discussionId)
    }

    // 수정: 도서 정보가 로드되면 해당 도서의 토론 목록도 로드
    LaunchedEffect(selectedBoard?.bookId) {
        selectedBoard?.let { board ->
            Log.d("DiscussionRoomRoute", "도서별 토론 목록 로드: ${board.bookId}")
            boardViewModel.loadBookBoards(board.bookId)
        }
    }

    // 수정: CreateDiscussionScreen의 생성 완료를 감지하기 위한 변수
    var previousCreateBoardTitle by remember { mutableStateOf("") }
    var previousCreateBoardContent by remember { mutableStateOf("") }
    var previousCreateBoardBookTitle by remember { mutableStateOf("") }

    // 수정: 토론 생성 완료 감지 및 화면 닫기
    LaunchedEffect(
        boardViewModel.createBoardTitle.value,
        boardViewModel.createBoardContent.value,
        boardViewModel.createBoardBookTitle.value,
        boardViewModel.isLoading.value
    ) {
        // 이전에 값이 있었는데 현재 모두 비어있고 로딩이 끝났다면 생성 완료
        if (previousCreateBoardTitle.isNotEmpty() &&
            previousCreateBoardContent.isNotEmpty() &&
            previousCreateBoardBookTitle.isNotEmpty() &&
            boardViewModel.createBoardTitle.value.isEmpty() &&
            boardViewModel.createBoardContent.value.isEmpty() &&
            boardViewModel.createBoardBookTitle.value.isEmpty() &&
            !boardViewModel.isLoading.value &&
            showAddDiscussionScreen) {

            Log.d("DiscussionRoomRoute", "토론 생성 완료 감지, 화면 닫기")
            showAddDiscussionScreen = false
            selectedBoard?.let { board ->
                boardViewModel.loadBookBoards(board.bookId) // 토론 목록 새로고침
            }
        }

        // 현재 값들을 저장
        previousCreateBoardTitle = boardViewModel.createBoardTitle.value
        previousCreateBoardContent = boardViewModel.createBoardContent.value
        previousCreateBoardBookTitle = boardViewModel.createBoardBookTitle.value
    }

    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("오류: $errorMessage")
            }
        }

        selectedBoard != null && selectedBook != null -> {
            // 수정: 조건부 렌더링으로 CreateDiscussionScreen과 DiscussionRoomScreen 전환
            if (showAddDiscussionScreen) {
                CreateDiscussionScreen(
                    navigator = navigator, // 수정: navigator 파라미터 추가
                    viewModel = boardViewModel // 수정: BoardViewModel 사용
                )
            } else {
                DiscussionRoomScreen(
                    selectedBoard = selectedBoard!!,
                    book = selectedBook!!,
                    discussionsForBook = bookBoards, // 수정: BoardViewModel의 bookBoards 사용
                    onBackClick = {
                        navigator.navigateUp()
                    },
                    onAddClick = {
                        showAddDiscussionScreen = true // AddDiscussion 화면 표시
                    },
                    onDiscussionClick = { clickedDiscussionId: Int -> // 수정: 타입 명시
                        try {
                            navigator.navigateToDiscussionTopic(clickedDiscussionId, 0)
                        } catch (e: Exception) {
                            Log.e("DiscussionRoomRoute", "❌ Navigation failed: ${e.message}", e)
                        }
                    }
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("토론 정보를 불러오는 중...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionRoomScreen(
    selectedBoard: BoardEntity, // 수정: BoardEntity 사용
    book: BookEntity,
    discussionsForBook: List<BoardEntity>, // 수정: List<BoardEntity> 사용
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onDiscussionClick: (Int) -> Unit = {}
) {
    discussionsForBook.forEachIndexed { index, discussion ->
        Log.d("DiscussionRoom", "📋 Discussion[$index]: boardId=${discussion.id}, bookTitle=${book.title}")
    }

    // 수정: 개설자 의견을 실제 토론 내용으로 생성
    val authorArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "사용자${selectedBoard.memberId}",
        content = selectedBoard.content, // 수정: BoardEntity의 content 사용
        createdAt = selectedBoard.createdAt,
        parentId = null
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "토론방",
                        style = baseBold,
                        color = Neutral60
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_emotionsharescreen_plus),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background02
                )
            )
        },
        containerColor = Background02,
        modifier = Modifier,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                DiscussionBookBox(
                    discussion = selectedBoard.toDiscussPageEntity(book.title), // 수정: 변환 함수 사용
                    book = book,
                    modifier = Modifier.padding(8.dp),
                    onClick = {}
                )
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(modifier = Modifier.width(5.dp))
            }
            item {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(Neutral60)) {
                            append("활성화된 ")
                        }
                        withStyle(style = SpanStyle(Button02)) {
                            append("토론")
                        }
                    },
                    modifier = Modifier.padding(start = 20.dp),
                    style = largeBold
                )
            }
            items(discussionsForBook) { discussion: BoardEntity -> // 수정: BoardEntity 타입 명시
                DiscussionRoomBox(
                    discussion = discussion.toDiscussPageEntity(book.title), // 수정: 변환 함수 사용
                    onClick = {
                        onDiscussionClick(discussion.id) // 수정: BoardEntity의 id 사용
                    },
                    modifier = Modifier.padding(15.dp),
                    argument = authorArgument
                )
            }
        }
    }
}

// 수정: BoardEntity를 DiscussPageEntity로 변환하는 확장 함수 (bookTitle 포함)
private fun BoardEntity.toDiscussPageEntity(bookTitle: String): DiscussPageEntity {
    return DiscussPageEntity(
        boardId = this.id,
        bookId = this.bookId,
        memberId = this.memberId,
        bookTitle = bookTitle, // 수정: 실제 도서 제목 사용
        content = this.content,
        createdAt = this.createdAt
    )
}