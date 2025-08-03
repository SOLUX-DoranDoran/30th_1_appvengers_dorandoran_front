// DiscussScreen.kt
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
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussViewModel

@Composable
fun DiscussRoute(
    navigator: DiscussNavigator,
    viewModel: DiscussViewModel = hiltViewModel()
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
    viewModel: DiscussViewModel
) {
    Log.d("DiscussDebug", "DiscussScreen 시작")

    val discussions by viewModel.discussions
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // 수정: 상태 로깅 추가
    LaunchedEffect(discussions, isLoading, errorMessage) {
        Log.d(
            "DiscussDebug",
            "상태 변경 - discussions size: ${discussions.size}, isLoading: $isLoading, errorMessage: $errorMessage"
        )
        discussions.forEachIndexed { index, discussion ->
            Log.d(
                "DiscussDebug",
                "Discussion $index: ${discussion.boardId}, ${discussion.bookTitle}"
            )
        }
    }

    // 수정: 임시 더미 argument 데이터 생성
    val dummyArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "토론자",
        content = "토론 내용입니다.",
        createdAt = "2024-01-01",
        parentId = null
    )

    Log.d("DiscussDebug", "Scaffold 렌더링 시작")

    Scaffold(
        topBar = {
            Log.d("DiscussDebug", "TopAppBar 렌더링")
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
        containerColor = Background03,
    ) { innerPadding ->
        Log.d("DiscussDebug", "Scaffold content 렌더링, innerPadding: $innerPadding")

        when {
            isLoading -> {
                Log.d("DiscussDebug", "로딩 상태 표시")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Log.e("DiscussDebug", "에러 상태: $errorMessage")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text("오류가 발생했습니다: $errorMessage")
                        Text("discussions size: ${discussions.size}")
                        Text("viewModel hash: ${viewModel.hashCode()}")
                    }
                }
            }

            discussions.isEmpty() -> {
                Log.w("DiscussDebug", "토론 목록이 비어있음")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text("토론이 없습니다.")
                        Text("뷰모델 상태 확인")
                        Text("isLoading: $isLoading")
                        Text("errorMessage: $errorMessage")
                    }
                }
            }

            else -> {
                Log.d("DiscussDebug", "LazyColumn 렌더링, discussions count: ${discussions.size}")
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(discussions) { discussion ->
                        Log.d("DiscussDebug", "DiscussionBox 렌더링: ${discussion.boardId}")
                        DiscussionBox(
                            discussion = discussion,
                            argument = dummyArgument,
                            onClick = {
                                Log.d("DiscussDebug", "토론 클릭: ${discussion.boardId}")
                                // 수정: 토론방으로 이동하는 네비게이션 추가
                                navigator.navigateToDiscussionRoom(discussion.boardId)
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