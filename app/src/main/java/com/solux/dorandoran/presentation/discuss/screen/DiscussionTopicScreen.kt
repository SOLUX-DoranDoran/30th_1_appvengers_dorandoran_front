package com.solux.dorandoran.presentation.discuss.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.component.ArgumentInputBox
import com.solux.dorandoran.core_ui.component.DiscussionCommentBox
import com.solux.dorandoran.core_ui.component.DiscussionTopicBox
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.presentation.discuss.viewmodel.BoardViewModel
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussViewModel

@Composable
fun DiscussionTopicRoute(
    navigator: DiscussNavigator,
    discussionId: Int,
    argumentId: Int, // 현재는 사용하지 않지만 나중을 위해 유지
    boardViewModel: BoardViewModel = hiltViewModel(), // 수정: BoardViewModel 추가
    discussViewModel: DiscussViewModel = hiltViewModel() // 수정: 댓글 전용 ViewModel
) {
    // 수정: BoardViewModel에서 토론 상세 정보 가져오기
    val selectedBoard by boardViewModel.selectedBoard
    val selectedBook by boardViewModel.selectedBook

    // 수정: 토론 상세 정보 로드
    LaunchedEffect(discussionId) {
        Log.d("DiscussionTopicRoute", "토론 상세 정보 로드: $discussionId")
        boardViewModel.loadBoardDetail(discussionId)
    }

    if (selectedBoard != null) {
        DiscussionTopicScreen(
            board = selectedBoard!!,
            book = selectedBook,
            boardViewModel = boardViewModel,
            discussViewModel = discussViewModel, // 수정: 댓글 전용 ViewModel 전달
            onBackClick = {
                navigator.navigateUp()
            }
        )
    } else {
        // 수정: 로딩 중이거나 데이터가 없는 경우
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("토론 정보를 불러오는 중...")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionTopicScreen(
    board: com.solux.dorandoran.domain.entity.BoardEntity, // 수정: BoardEntity 사용
    book: com.solux.dorandoran.domain.entity.BookEntity?, // 수정: BookEntity 추가
    boardViewModel: BoardViewModel,
    discussViewModel: DiscussViewModel, // 수정: 댓글 전용 ViewModel
    onBackClick: () -> Unit = {}
) {
    // 수정: 댓글 관련 상태들을 DiscussViewModel에서 가져오기
    val commentInput by discussViewModel.commentInput
    val organizedComments = discussViewModel.getOrganizedComments()
    val mainComments = discussViewModel.getMainComments()
    val isLoading by discussViewModel.isLoading
    val errorMessage by discussViewModel.errorMessage

    var argumentText by remember { mutableStateOf("") } // argument 입력용

    // 수정: 토론 댓글들 로드
    LaunchedEffect(board.id) {
        Log.d("DiscussionTopicScreen", "댓글 로드 시작: ${board.id}")
        discussViewModel.loadComments(board.id)
    }

    // 수정: 개설자 의견을 실제 토론 내용으로 생성
    val authorArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "사용자${board.memberId}",
        content = board.content, // 수정: 실제 토론 내용 사용
        createdAt = board.createdAt,
        parentId = null
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = book?.title ?: "도서 정보 로딩 중...", // 수정: 실제 도서 제목 사용
                        style = baseBold,
                        color = Neutral60
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background02
                )
            )
        },
        containerColor = Background02,
        bottomBar = {
            ArgumentInputBox(
                inputText = argumentText,
                onInputChange = { argumentText = it },
                onSubmit = {
                    if (argumentText.isNotBlank()) {
                        // 수정: DiscussViewModel의 댓글 작성 메서드 사용
                        discussViewModel.updateCommentInput(argumentText)
                        discussViewModel.createComment(board.id)
                        argumentText = ""
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    ) { innerPadding ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("댓글을 불러오는 중 오류가 발생했습니다")
                        Text(errorMessage!!)
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    // 수정: 토론 주제 표시 (BoardEntity 사용)
                    item {
                        DiscussionTopicBox(
                            discussion = board.toDiscussPageEntity(), // 수정: 변환 함수 사용
                            argument = authorArgument,
                            onClick = { /* 토론 상세에서는 클릭 불필요 */ },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // 수정: 개설자 의견 표시
                    item {
                        DiscussionCommentBox(
                            discussion = board.toDiscussPageEntity(),
                            argument = authorArgument,
                            comments = organizedComments, // 수정: List<DiscussCommentEntity> 전달
                            onAddComment = { parentId ->
                                // 수정: 대댓글 작성 로직
                                discussViewModel.toggleCommentInput(parentId)
                                discussViewModel.setReplyTarget(parentId)
                            },
                            isInputVisible = false, // 수정: 기본값으로 설정
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    // 수정: 댓글들 표시
                    items(organizedComments) { comment ->
                        DiscussionCommentBox(
                            discussion = board.toDiscussPageEntity(),
                            argument = authorArgument,
                            comments = organizedComments, // 수정: List<DiscussCommentEntity> 전달
                            onAddComment = { parentId ->
                                // 수정: 대댓글 작성 로직
                                discussViewModel.toggleCommentInput(parentId)
                                discussViewModel.setReplyTarget(parentId)
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

// 수정: BoardEntity를 DiscussPageEntity로 변환하는 확장 함수
private fun com.solux.dorandoran.domain.entity.BoardEntity.toDiscussPageEntity(): com.solux.dorandoran.domain.entity.DiscussPageEntity {
    return com.solux.dorandoran.domain.entity.DiscussPageEntity(
        boardId = this.id,
        bookId = this.bookId,
        memberId = this.memberId,
        bookTitle = "책 제목", // TODO: 실제 Book 정보에서 가져와야 함
        content = this.content,
        createdAt = this.createdAt
    )
}