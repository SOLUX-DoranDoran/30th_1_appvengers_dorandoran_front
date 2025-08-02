package com.solux.dorandoran.presentation.discuss.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.core_ui.component.DiscussionTopicBox
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.component.ArgumentInputBox
import com.solux.dorandoran.core_ui.component.DiscussionCommentBox
import com.solux.dorandoran.domain.entity.DiscussPageEntity
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.presentation.discuss.navigation.DiscussNavigator
import com.solux.dorandoran.presentation.discuss.viewmodel.ArgumentViewModel
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussViewModel

@Composable
fun DiscussionTopicRoute(
    navigator: DiscussNavigator,
    discussionId: Int,
    argumentId: Int, // 현재는 사용하지 않지만 나중을 위해 유지
    viewModel: DiscussViewModel = hiltViewModel()
) {
    val discussion = viewModel.getDiscussionById(discussionId)

    if (discussion != null) {
        DiscussionTopicScreen(
            discussion = discussion,
            onBackClick = { navigator.navigateUp() },
            onItemClick = { /* 필요시 구현 */ }
        )
    } else {
        Text("토론을 찾을 수 없습니다")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionTopicScreen(
    discussion: DiscussPageEntity,
    onBackClick: () -> Unit = {},
    argumentViewModel: ArgumentViewModel = hiltViewModel(),
    onItemClick: (DiscussPageEntity) -> Unit
) {
    val commentInputMap by argumentViewModel.activeCommentInputMap
    var argumentText by remember { mutableStateOf("") } // argument 입력용

    // 토론 댓글들 로드
    LaunchedEffect(discussion.boardId) {
        argumentViewModel.loadCommentsForDiscussion(discussion.boardId)
    }

    // 🎯 임시 더미 데이터로 개설자 의견 생성
    val authorArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "사용자${discussion.memberId}", // memberId로 임시 닉네임 생성
        content = "이것은 임시 개설자 의견입니다. 실제로는 API에서 가져와야 합니다.",
        createdAt = discussion.createdAt,
        parentId = null
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = discussion.bookTitle,
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
                modifier = Modifier.padding(8.dp),
                onSubmit = {
                    // argument 제출
                    println("새 의견 제출: $argumentText")
                    argumentText = ""
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                DiscussionTopicBox(
                    discussion = discussion,
                    argument = authorArgument, // memberId 기반 더미 데이터
                    onClick = {},
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
            }

            items(argumentViewModel.getMainArguments()) { argument ->
                val commentsForArgument = argumentViewModel.getCommentsForArgument(argument.id)
                DiscussionCommentBox(
                    discussion = discussion,
                    argument = argument,
                    comments = commentsForArgument,
                    onAddComment = { argumentViewModel.toggleCommentInput(argument.id) },
                    isInputVisible = commentInputMap[argument.id] == true
                )
            }
        }
    }
}
