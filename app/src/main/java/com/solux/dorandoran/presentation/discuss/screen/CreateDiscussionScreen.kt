package com.solux.dorandoran.presentation.discuss.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.presentation.discuss.viewmodel.BoardViewModel

@Composable
fun CreateDiscussionRoute(
    navigator: DiscussNavigator,
    viewModel: BoardViewModel = hiltViewModel() // 수정: BoardViewModel 사용
) {
    CreateDiscussionScreen(
        navigator = navigator,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDiscussionScreen(
    navigator: DiscussNavigator,
    viewModel: BoardViewModel // 수정: BoardViewModel 사용
) {
    // 수정: BoardViewModel의 토론 생성 관련 상태들 사용
    val title by viewModel.createBoardTitle
    val content by viewModel.createBoardContent
    val bookTitle by viewModel.createBoardBookTitle
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // 수정: 토론 생성 성공 시 네비게이션 처리
    LaunchedEffect(isLoading) {
        if (!isLoading && errorMessage == null &&
            title.isEmpty() && content.isEmpty() && bookTitle.isEmpty()) {
            // 폼이 초기화되었다면 생성 성공으로 간주하고 뒤로 이동
            Log.d("CreateDiscussionScreen", "토론 생성 성공, 이전 화면으로 이동")
            navigator.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "토론 개설",
                        style = baseBold,
                        color = Neutral60
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            Log.d("CreateDiscussionScreen", "뒤로가기 클릭")
                            navigator.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = "뒤로가기",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background02
                )
            )
        },
        containerColor = Background02
    ) { innerPadding ->

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("토론을 생성하는 중...")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                // 수정: 도서 제목 입력
                OutlinedTextField(
                    value = bookTitle,
                    onValueChange = { newValue ->
                        Log.d("CreateDiscussionScreen", "도서 제목 입력: $newValue")
                        viewModel.updateCreateBoardBookTitle(newValue)
                    },
                    label = { Text("도서 제목") },
                    placeholder = { Text("토론할 도서의 제목을 입력해주세요") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 수정: 토론 제목 입력
                OutlinedTextField(
                    value = title,
                    onValueChange = { newValue ->
                        Log.d("CreateDiscussionScreen", "토론 제목 입력: $newValue")
                        viewModel.updateCreateBoardTitle(newValue)
                    },
                    label = { Text("토론 제목") },
                    placeholder = { Text("토론 주제를 입력해주세요") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 수정: 토론 내용 입력
                OutlinedTextField(
                    value = content,
                    onValueChange = { newValue ->
                        Log.d("CreateDiscussionScreen", "토론 내용 입력: ${newValue.take(50)}...")
                        viewModel.updateCreateBoardContent(newValue)
                    },
                    label = { Text("토론 내용") },
                    placeholder = { Text("토론하고 싶은 내용을 자세히 작성해주세요") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5,
                    maxLines = 10
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 수정: 에러 메시지 표시
                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                // 수정: 토론 생성 버튼
                Button(
                    onClick = {
                        Log.d("CreateDiscussionScreen", "토론 생성 버튼 클릭")
                        viewModel.createBoard()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && title.isNotBlank() && content.isNotBlank() && bookTitle.isNotBlank()
                ) {
                    Text("토론 개설하기")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 수정: 입력 가이드
                Text(
                    text = "* 모든 필드를 입력해주세요\n* 토론 내용은 구체적으로 작성해주세요\n* 다른 사용자들이 참여하고 싶어할 만한 주제로 작성해주세요",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    color = Neutral60,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}