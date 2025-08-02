package com.solux.dorandoran.presentation.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.component.EmotionShareListItem
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.presentation.mypage.navigation.MypageNavigator
import com.solux.dorandoran.presentation.mypage.viewmodel.EmotionShareViewModel


@Composable
fun EmotionShareRoute(
    navigator: MypageNavigator,
    viewModel: EmotionShareViewModel = hiltViewModel()
) {
    EmotionShare(
        navigator = navigator,
        viewModel = viewModel
    )
}

@Composable
fun EmotionShare(
    navigator: MypageNavigator,
    viewModel: EmotionShareViewModel
) {
    val quoteList by viewModel.quoteList
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background02)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            EmotionShareHeader(
                onBackClick = { navigator.navigateToHome() }
            )

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Button02)
                    }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = errorMessage ?: "오류가 발생했습니다.",
                                color = Neutral60
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "다시 시도",
                                color = Button02,
                                modifier = Modifier.clickable {
                                    viewModel.clearErrorMessage()
                                    viewModel.refreshQuotes()
                                }
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        contentPadding = PaddingValues(bottom = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(quoteList) { index, quote ->
                            EmotionShareListItem(
                                quote = quote,
                                itemIndex = index,
                                onLikeClick = {
                                    viewModel.toggleLike(quote.id)
                                }
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navigator.navigateToEmotionShareNew() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 30.dp)
                .size(57.dp)
                .shadow(
                    elevation = 2.23.dp,
                    shape = CircleShape
                ),
            shape = CircleShape,
            containerColor = Button02,
            contentColor = Background01
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_emotionsharescreen_plus),
                contentDescription = null,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
private fun EmotionShareHeader(
    onBackClick: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )

            Text(
                text = "감성 공유",
                style = baseBold,
                color = Neutral60,
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}