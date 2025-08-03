// DiscussScreen.kt - 수정된 버전
package com.solux.dorandoran.presentation.discuss.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.core_ui.component.DiscussionBox
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Background03
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.presentation.discuss.viewmodel.BoardViewModel // 수정: BoardViewModel 사용

@Composable
fun DiscussRoute(
    navigator: DiscussNavigator,
    viewModel: BoardViewModel = hiltViewModel() // 수정: BoardViewModel 사용
) {
    Log.d("DiscussDebug", "DiscussRoute 호출됨")

    DiscussScreen(
        navigator = navigator,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussScreen(
    navigator: DiscussNavigator,
    viewModel: BoardViewModel // 수정: BoardViewModel 사용
) {
    Log.d("DiscussDebug", "DiscussScreen 시작")

    // 수정: BoardViewModel의 상태들 사용
    val boards by viewModel.boards
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // 수정: 화면 진입 시 토론 목록 로드
    LaunchedEffect(Unit) {
        Log.d("DiscussDebug", "토론 목록 로드 시작")
        viewModel.loadBoards()
    }

    // 수정: 상태 로깅 추가
    LaunchedEffect(boards, isLoading, errorMessage) {
        Log.d(
            "DiscussDebug",
            "상태 변경 - boards size: ${boards.size}, isLoading: $isLoading, errorMessage: $errorMessage"
        )
        boards.forEachIndexed { index, board ->
            Log.d(
                "DiscussDebug",
                "Board $index: ${board.id}, ${board.title}"
            )
        }
    }

    // 수정: 임시 더미 argument 데이터 생성 (나중에 제거 예정)
    val dummyArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "토론자",
        content = "토론 내용입니다.",
        createdAt = "2024-01-01T10:00:00",
        parentId = null
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "토론",
                        style = baseBold,
                        color = Neutral60
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background02
                )
            )
        },
        containerColor = Background02
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
                        Text("오류가 발생했습니다")
                        Text(errorMessage!!)
                        Text("디버그 정보:")
                        Text("isLoading: $isLoading")
                        Text("errorMessage: $errorMessage")
                    }
                }
            }

            else -> {
                Log.d("DiscussDebug", "LazyColumn 렌더링, boards count: ${boards.size}")
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(boards) { board ->
                        Log.d("DiscussDebug", "DiscussionBox 렌더링: ${board.id}")
                        DiscussionBox(
                            discussion = board.toDiscussPageEntity(), // 수정: BoardEntity를 DiscussPageEntity로 변환
                            argument = dummyArgument,
                            onClick = {
                                Log.d("DiscussDebug", "토론 클릭: ${board.id}")
                                // 수정: 토론방으로 이동하는 네비게이션 추가
                                navigator.navigateToDiscussionRoom(board.id)
                            },
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }

    Log.d("DiscussDebug", "DiscussScreen 끝")
}

// 수정: BoardEntity를 DiscussPageEntity로 변환하는 확장 함수 추가
private fun com.solux.dorandoran.domain.entity.BoardEntity.toDiscussPageEntity(): com.solux.dorandoran.domain.entity.DiscussPageEntity {
    return com.solux.dorandoran.domain.entity.DiscussPageEntity(
        boardId = this.id,
        bookId = this.bookId,
        memberId = this.memberId,
        bookTitle = "책 제목", // 수정: 실제로는 Book 정보에서 가져와야 함
        content = this.content,
        createdAt = this.createdAt
    )
}