package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background03
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.baseRegular
import com.solux.dorandoran.core_ui.theme.smallRegular
import com.solux.dorandoran.domain.entity.QuoteEntity

@Composable
fun QuoteContentRow(
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "\"",
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            color = Button02,
            modifier = Modifier.width(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = content,
            style = baseRegular,
            color = Neutral60,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomActionRow(
    nickname: String,
    isLiked: Boolean,
    likeCount: Int,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 사용자 프로필 정보
        UserProfileSection(nickname = nickname)

        // 좋아요 버튼 - ReviewDetailCard 스타일과 동일
        LikeActionButton(
            isLiked = isLiked,
            likeCount = likeCount,
            onLikeClick = onLikeClick
        )
    }
}

@Composable
fun UserProfileSection(
    nickname: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(1.dp, Button02, CircleShape)
                .background(Background03)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = nickname,
            style = smallRegular,
            color = Neutral60
        )
    }
}

@Composable
fun LikeActionButton(
    isLiked: Boolean,
    likeCount: Int,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable { onLikeClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isLiked) {
                        R.drawable.ic_emotionsharescreen_heart_fill
                    } else {
                        R.drawable.ic_emotionsharesreen_heart
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        }

        Text(
            text = likeCount.toString(),
            style = smallRegular,
            color = if (isLiked) Button02 else Neutral60
        )
    }
}

@Composable
fun EmotionShareListItem(
    quote: QuoteEntity,
    itemIndex: Int,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOddPosition = (itemIndex % 2 != 0)

    BubbleCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        isOddPosition = isOddPosition
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // 책 제목
            Text(
                text = quote.bookTitle,
                style = baseBold,
                color = Neutral60,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 인용문 내용
            QuoteContentRow(content = quote.content)

            Spacer(modifier = Modifier.height(16.dp))

            // 사용자 정보 및 좋아요 버튼
            BottomActionRow(
                nickname = quote.nickname,
                isLiked = quote.isLiked, // 수정: QuoteEntity에서 직접 가져오기
                likeCount = quote.likeCount, // 수정: QuoteEntity에서 직접 가져오기
                onLikeClick = onLikeClick
            )
        }
    }
}