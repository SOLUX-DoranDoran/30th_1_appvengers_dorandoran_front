package com.solux.dorandoran.presentation.discuss.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.entity.DiscussPageEntity
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussViewModel
import com.solux.dorandoran.presentation.discuss.viewmodel.ArgumentViewModel

@Composable
fun DiscussionRoomRoute(
    navigator: DiscussNavigator,
    discussionId: Int,
    viewModel: DiscussViewModel = hiltViewModel(),
    argumentViewModel: ArgumentViewModel = hiltViewModel()
) {
    val selectedDiscussion = viewModel.getDiscussionById(discussionId)
    if (selectedDiscussion != null) {
        val book = viewModel.getBookInfoByBookId(selectedDiscussion.bookId)
        val bookDiscussions = viewModel.getDiscussionsForBook(selectedDiscussion.bookTitle)

        book?.let { bookInfo ->
            DiscussionRoomScreen(
                selectedBook = selectedDiscussion,
                book = book,
                discussionsForBook = bookDiscussions,
                argumentViewModel = argumentViewModel,
                onBackClick = { navigator.navigateUp() },
                onAddClick = {},
                onDiscussionClick = { clickedDiscussionId ->
                    navigator.navigateToDiscussionTopic(clickedDiscussionId, 0)
                }
            )
        }
    } else {
        Text("선택된 책을 찾을 수 없습니다")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscussionRoomScreen(
    selectedBook: DiscussPageEntity,
    book: BookEntity,
    discussionsForBook: List<DiscussPageEntity>,
    argumentViewModel: ArgumentViewModel,
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onDiscussionClick: (Int) -> Unit = {}
) {
    val authorArgument = DiscussCommentEntity(
        id = 999,
        memberNickname = "사용자${selectedBook.memberId}", // memberId로 임시 닉네임 생성
        content = "이것은 임시 개설자 의견입니다. 실제로는 API에서 가져와야 합니다.",
        createdAt = selectedBook.createdAt,
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
                            contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_emotionsharescreen_plus),
                            contentDescription = null)
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
                    discussion = selectedBook,
                    book = book,
                    modifier = Modifier
                        .padding(8.dp),
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
            items(discussionsForBook) { discussion: DiscussPageEntity ->
                DiscussionRoomBox(
                    discussion = discussion,
                    onClick = {
                        println("Box Clicked")
                        onDiscussionClick(discussion.boardId) },
                    modifier = Modifier
                        .padding(15.dp),
                    argument = authorArgument
                )
            }
        }
    }
}
