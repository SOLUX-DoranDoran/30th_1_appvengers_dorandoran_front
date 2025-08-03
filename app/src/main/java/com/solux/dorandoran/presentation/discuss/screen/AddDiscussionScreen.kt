package com.solux.dorandoran.presentation.discuss.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral70
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.presentation.discuss.viewmodel.DiscussViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiscussionScreen(
    viewModel: DiscussViewModel = hiltViewModel(),
    bookId: Int,
    onBackClick: () -> Unit = {},
    onDiscussionCreated: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val bookInfo = viewModel.getBookInfoByBookId(bookId)
    val discussionTopic by viewModel.discussionTopic
    val argumentContent by viewModel.argumentContent

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "토론방 개설",
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
            Button(
                onClick = {
                    if (discussionTopic.isNotBlank() && argumentContent.isNotBlank()) {
                        val success = viewModel.createDiscussion(bookId)
                        if (success) {
                            onDiscussionCreated() // 생성 완료 시 이전 화면으로
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Button02,
                    contentColor = Background01
                ),
                enabled = discussionTopic.isNotBlank() && argumentContent.isNotBlank()
            ) {
                Text(
                    text = "개설하기",
                    style = baseBold
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Background02)
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 25.dp)
        ) {
            Text(
                text = "토론 주제",
                style = baseBold,
                color = Neutral60
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = discussionTopic,
                onValueChange = { viewModel.onDiscussionTopicChange(it) },
                placeholder = {
                    Text(
                        text = "토론하고 싶은 주제를 입력하세요",
                        color = Neutral70
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background01,
                    unfocusedContainerColor = Background01,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .border(width = 1.dp, color = Button02, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "내 의견 쓰기",
                style = baseBold,
                color = Neutral60
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = argumentContent,
                onValueChange = { viewModel.onArgumentContentChange(it) },
                placeholder = {
                    Text(
                        text = "토론 주제에 대한 당신의 의견을 작성해주세요",
                        color = Neutral70
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background01,
                    unfocusedContainerColor = Background01,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .border(width = 1.dp, color = Button02, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}
