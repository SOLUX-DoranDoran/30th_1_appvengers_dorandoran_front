package com.solux.dorandoran.presentation.review.screen

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
import com.solux.dorandoran.core_ui.component.BookInfoCard
import com.solux.dorandoran.core_ui.component.CommentInputSection
import com.solux.dorandoran.core_ui.component.ReviewDetailCard
import com.solux.dorandoran.core_ui.component.ReviewWriteSection
import com.solux.dorandoran.core_ui.theme.Background02
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral80
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.presentation.review.navigation.ReviewNavigator
import com.solux.dorandoran.presentation.review.viewmodel.ReviewViewModel

@Composable
fun ReviewDetailRoute(
    navigator: ReviewNavigator,
    bookId: Long,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    LaunchedEffect(bookId) {
        viewModel.initializeWithBookId(bookId)
    }

    ReviewDetailScreen(
        navigator = navigator,
        viewModel = viewModel,
        bookId = bookId
    )
}

@Composable
fun ReviewDetailScreen(
    navigator: ReviewNavigator,
    viewModel: ReviewViewModel,
    bookId: Long
) {
    val selectedTabIndex by viewModel.selectedTabIndex
    val currentBook by viewModel.currentBook
    val bookReviews by viewModel.bookReviews
    val selectedReviewForComment by viewModel.selectedReviewForComment
    val commentInputText by viewModel.commentInputText
    val newReviewRating by viewModel.newReviewRating
    val newReviewContent by viewModel.newReviewContent
    val isLoading by viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background02)
    ) {
        ReviewHeader(
            onBackClick = { navigator.navController.popBackStack() },
            onSearchClick = {}
        )

        if (isLoading) {
            Text("Loading book details...", modifier = Modifier.padding(16.dp))
        } else {
            currentBook?.let { book ->
                BookInfoCard(
                    book = book,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } ?: run {
                Text("Book details not available.", modifier = Modifier.padding(16.dp))
            }
        }
        ReviewTabRow(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index -> viewModel.updateSelectedTab(index) }
        )

        when (selectedTabIndex) {
            0 -> {
                UserReviewsTab(
                    reviews = viewModel.getReviewsWithDetails(),
                    onLikeClick = { reviewId -> viewModel.toggleLike(reviewId) },
                    onCommentClick = { reviewId -> viewModel.selectReviewForComment(reviewId) },
                    onToggleComments = { reviewId -> viewModel.toggleCommentsVisibility(reviewId) }
                )
            }
            1 -> {
                WriteReviewTab(
                    rating = newReviewRating,
                    content = newReviewContent,
                    onRatingChange = { rating -> viewModel.updateNewReviewRating(rating) },
                    onContentChange = { content -> viewModel.updateNewReviewContent(content) },
                    onSubmit = { viewModel.submitReview() },
                )
            }
        }
    }

    if (selectedReviewForComment != null) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            CommentInputSection(
                modifier = Modifier,
                value = commentInputText,
                onValueChange = { text -> viewModel.updateCommentInput(text) },
                onSubmit = { viewModel.submitComment() },
                onCancel = { viewModel.selectReviewForComment(null) }
            )
        }
    }

}

@Composable
private fun ReviewHeader(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit
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
                text = "리뷰",
                style = baseBold,
                color = Neutral60,
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSearchClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun ReviewTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("사용자 평", "리뷰 작성하기")

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
private fun UserReviewsTab(
    reviews: List<com.solux.dorandoran.domain.entity.ReviewDetailEntity>,
    onLikeClick: (Long) -> Unit,
    onCommentClick: (Long) -> Unit,
    onToggleComments: (Long) -> Unit
) {
    if (reviews.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "아직 등록된 리뷰가 없어요",
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
                ReviewDetailCard(
                    review = review,
                    onLikeClick = { onLikeClick(review.id) },
                    onCommentClick = { onCommentClick(review.id) },
                    onToggleComments = { onToggleComments(review.id) }
                )
            }
        }
    }
}

@Composable
private fun WriteReviewTab(
    rating: Int,
    content: String,
    onRatingChange: (Int) -> Unit,
    onContentChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item {
            ReviewWriteSection(
                rating = rating,
                content = content,
                onRatingChange = onRatingChange,
                onContentChange = onContentChange,
                onSubmit = onSubmit
            )
        }
    }
}