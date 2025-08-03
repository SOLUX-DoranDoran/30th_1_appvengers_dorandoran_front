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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.component.EmotionShareListItem
import com.solux.dorandoran.core_ui.component.RecentReviewItem
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral80
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.smallBold
import com.solux.dorandoran.presentation.mypage.navigation.MypageNavigator
import com.solux.dorandoran.presentation.mypage.viewmodel.MyPageViewModel

@Composable
fun MypageRoute(
    navigator: MypageNavigator,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initializeMyPage()
    }

    MyPageScreen(
        navigator = navigator,
        viewModel = viewModel
    )
}

@Composable
fun MyPageScreen(
    navigator: MypageNavigator,
    viewModel: MyPageViewModel
) {
    val selectedTabIndex by viewModel.selectedTabIndex
    val myReviews by viewModel.myReviews
    val myQuotes by viewModel.myQuotes
    val userProfile by viewModel.userProfile
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background02)
    ) {
        // 사용자 프로필 헤더 (기존 마이페이지 상단 부분)
        userProfile?.let { profile ->
            MyPageHeader(
                nickname = profile.nickname,
                onSettingsClick = { /* 설정 클릭 */ }
            )
        }

        // 탭 메뉴
        MyPageTabRow(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index -> viewModel.updateSelectedTab(index) }
        )

        // 탭별 콘텐츠
        when (selectedTabIndex) {
            0 -> {
                MyReviewsTab(
                    reviews = myReviews,
                    isLoading = isLoading,
                    onReviewClick = {}
                )
            }
            1 -> {
                MyQuotesTab(
                    quotes = myQuotes,
                    isLoading = isLoading,
                    onLikeClick = { quoteId -> viewModel.toggleQuoteLike(quoteId) }
                )
            }
        }
    }
}

@Composable
private fun MyPageHeader(
    nickname: String,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Button02)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "마이페이지",
                style = baseBold,
                color = Neutral60
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_settings),
                contentDescription = "설정",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsClick() }
            )
        }

        // 사용자 프로필 정보 (기존 코드에서 가져온 부분)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 프로필 이미지 (원형)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Background01,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                    text = nickname,
                    style = baseBold,
                    color = Neutral60
                )

            Spacer(modifier = Modifier.width(40.dp))

                Text(
                    text = "안녕하세요 !",
                    style = smallBold,
                    color = Button02,
                    modifier = Modifier
                        .background(
                            color = androidx.compose.ui.graphics.Color.White,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }

@Composable
private fun MyPageTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("내 리뷰 보기", "내 감성글귀 보기")

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = Background02,
        contentColor = Neutral60,
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Button02,
                height = 2.dp
            )
        },
        divider = {
            HorizontalDivider(
                color = Neutral80,
                thickness = 1.dp
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Text(
                    text = title,
                    style = baseBold,
                    color = if (selectedTabIndex == index) Neutral60 else Neutral80,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun MyReviewsTab(
    reviews: List<com.solux.dorandoran.domain.entity.ReviewListEntity>,
    isLoading: Boolean,
    onReviewClick: (Long) -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "내 리뷰를 불러오는 중...",
                style = baseBold,
                color = Neutral80
            )
        }
    } else if (reviews.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "아직 작성한 리뷰가 없어요",
                    style = baseBold,
                    color = Neutral80
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "첫 번째 리뷰를 작성해보세요!",
                    style = baseBold,
                    color = Neutral80
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reviews) { review ->
                RecentReviewItem(
                    review = review,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun MyQuotesTab(
    quotes: List<com.solux.dorandoran.domain.entity.QuoteEntity>,
    isLoading: Boolean,
    onLikeClick: (Long) -> Unit
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "내 감성글귀를 불러오는 중...",
                style = baseBold,
                color = Neutral80
            )
        }
    } else if (quotes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "아직 작성한 감성글귀가 없어요",
                    style = baseBold,
                    color = Neutral80
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "첫 번째 감성글귀를 작성해보세요!",
                    style = baseBold,
                    color = Neutral80
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(quotes) { index, quote ->
                EmotionShareListItem(
                    quote = quote,
                    itemIndex = index,
                    onLikeClick = { onLikeClick(quote.id) }
                )
            }
        }
    }
}