package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Background03
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.baseRegular
import com.solux.dorandoran.core_ui.theme.largeBold
import com.solux.dorandoran.domain.entity.DiscussionEntity
import com.solux.dorandoran.domain.entity.EmotionShareEntity
import com.solux.dorandoran.domain.entity.QuoteEntity
import com.solux.dorandoran.domain.entity.RecommendedBookEntity
import com.solux.dorandoran.domain.entity.ReviewListEntity

@Composable
fun BookRecommendationSection(
    books: List<RecommendedBookEntity>,
    onBookClick: (RecommendedBookEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(363.dp)
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            SectionTitle(
                mainText = "소설 부문 ",
                highlightText = "추천 도서",
            )
        }

        Card(
            modifier = Modifier
                .width(363.dp)
                .height(230.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Background03,
                            Button02
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                ),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Background01
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                books.take(3).forEach { book ->
                    BookRecommendationItem(
                        book = book,
                        onClick = { onBookClick(book) }
                    )
                }
            }
        }
    }
}

@Composable
fun RecentReviewSection(
    review: ReviewListEntity,
    onReviewClick: (Long) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            title = "최근 리뷰",
            onMoreClick = onMoreClick,
            modifier = Modifier
                .width(363.dp)
                .padding(bottom = 12.dp)
        )

        review.let {
            RecentReviewItem(
                review = review,
                onClick = { onReviewClick(review.reviewId) }
            )
        }
    }
}

@Composable
fun HotDiscussionsSection(
    discussion: DiscussionEntity?,
    onDiscussionClick: (Long) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            title = "지금 핫한 토론",
            onMoreClick = onMoreClick,
            modifier = Modifier
                .width(363.dp)
                .padding(bottom = 12.dp)
        )

        discussion?.let {
            DiscussionItem(
                discussion = it,
                onClick = { onDiscussionClick(it.boardId) }
            )
        }
    }
}

@Composable
fun EmotionShareSection(
    quote: QuoteEntity?,
    onEmotionClick: (Long) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            title = "감성 공유",
            onMoreClick = onMoreClick,
            modifier = Modifier
                .width(363.dp)
                .padding(bottom = 12.dp)
        )

        quote?.let {
            EmotionShareItem(
                emotion = EmotionShareEntity(
                    id = it.id,
                    bookId = it.bookId,
                    bookTitle = it.bookTitle,
                    coverImageUrl = it.coverImageUrl,
                    content = it.content,
                    createdAt = it.createdAt,
                    nickname = it.nickname,
                    profileImage = it.profileImage ?: ""
                ),
                onClick = { onEmotionClick(it.id) }
            )
        }
    }
}

@Composable
private fun SectionTitle(
    mainText: String,
    highlightText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mainText,
            style = if (mainText.contains("소설 부문")) largeBold else baseBold,
            color = Neutral60
        )
        Text(
            text = highlightText,
            style = if (mainText.contains("소설 부문")) largeBold else baseBold,
            color = Button02
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (title) {
            "최근 리뷰" -> {
                TitleWithHighlight(
                    normalText = "최근 ",
                    highlightText = "리뷰"
                )
            }
            "지금 핫한 토론" -> {
                TitleWithHighlight(
                    normalText = "지금 핫한 ",
                    highlightText = "토론"
                )
            }
            "감성 공유" -> {
                TitleWithHighlight(
                    normalText = "감성 ",
                    highlightText = "공유"
                )
            }
            else -> {
                ColoredText(
                    text = title,
                    isHighlight = false
                )
            }
        }

        TextButton(onClick = onMoreClick) {
            Text(
                text = "더보기",
                style = baseRegular,
                color = Neutral60
            )
        }
    }
}

@Composable
private fun ColoredText(
    text: String,
    isHighlight: Boolean
) {
    Text(
        text = text,
        style = baseBold,
        color = if (isHighlight) Button02 else Neutral60
    )
}

@Composable
private fun TitleWithHighlight(
    normalText: String,
    highlightText: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ColoredText(
            text = normalText,
            isHighlight = false
        )
        ColoredText(
            text = highlightText,
            isHighlight = true
        )
    }
}