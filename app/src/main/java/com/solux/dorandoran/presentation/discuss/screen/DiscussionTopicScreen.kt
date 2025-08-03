package com.solux.dorandoran.presentation.discuss.screen

import android.util.Log
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
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussCommentViewModel
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussViewModel
import androidx.compose.foundation.lazy.items

@Composable
fun DiscussionTopicRoute(
    navigator: DiscussNavigator,
    discussionId: Int,
    argumentId: Int, // 현재는 사용하지 않지만 나중을 위해 유지
    viewModel: DiscussViewModel = hiltViewModel(),
    discussCommentViewModel: DiscussCommentViewModel = hiltViewModel() // 수정: 댓글 ViewModel 추가
) {

    val discussion = viewModel.getDiscussionById(discussionId)

    if (discussion != null) {
        DiscussionTopicScreen(
            discussion = discussion,
            viewModel = viewModel, // 수정: 메인 ViewModel 전달
            discussCommentViewModel = discussCommentViewModel, // 수정: 댓글 ViewModel 전달
            onBackClick = {
                navigator.navigateUp()
            },
            onItemClick = { /* 필요시 구현 */ }
        )
    } else {
        Log.e("DiscussionTopicRoute", "❌ Discussion not found for discussionId: $discussionId")
        Text("토론을 찾을 수 없습니다")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionTopicScreen(
    discussion: DiscussPageEntity,
    onBackClick: () -> Unit = {},
    viewModel: DiscussViewModel = hiltViewModel(), // 수정: 메인 ViewModel 추가
    discussCommentViewModel: DiscussCommentViewModel = hiltViewModel(), // 수정: 기본값 추가
    onItemClick: (DiscussPageEntity) -> Unit = {}
) {
    // 수정: 댓글 관련 상태들을 ViewModel에서 가져오기
    val commentInputMap by discussCommentViewModel.activeCommentInputMap
    val commentInput by discussCommentViewModel.commentInput
    val organizedComments = discussCommentViewModel.getOrganizedComments()
    val mainComments = discussCommentViewModel.getMainComments()

    var argumentText by remember { mutableStateOf("") } // argument 입력용

    // 수정: 토론 댓글들 로드
    LaunchedEffect(discussion.boardId) {
        discussCommentViewModel.loadComments(discussion.boardId)
    }

    // 수정: 개설자 의견을 실제 토론 내용으로 생성
    val authorArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "사용자${discussion.memberId}",
        content = discussion.content, // 수정: 실제 토론 내용 사용
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
                    if (argumentText.isNotBlank()) {
                        discussCommentViewModel.updateCommentInput(argumentText) //수정: 먼저 입력값 설정
                        discussCommentViewModel.createComment(discussion.boardId) //수정: 그 다음 댓글 생성
                        argumentText = "" //수정: 입력 필드 초기화
                    }
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
                    argument = authorArgument,
                    onClick = {},
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
            }

            // 수정: 원댓글들을 표시
            items(mainComments) { comment ->
                val repliesForComment = discussCommentViewModel.getRepliesForComment(comment.id)
                DiscussionCommentBox(
                    discussion = discussion,
                    argument = comment, // 수정: comment를 argument로 사용
                    comments = repliesForComment, // 수정: 해당 댓글의 대댓글들
                    onAddComment = {
                        discussCommentViewModel.toggleCommentInput(comment.id)
                    },
                    isInputVisible = commentInputMap[comment.id] == true
                )
            }
        }
    }
}