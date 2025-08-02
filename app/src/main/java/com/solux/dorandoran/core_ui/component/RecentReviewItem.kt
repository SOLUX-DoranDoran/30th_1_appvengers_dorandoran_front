package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Background03
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral70
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.smallRegular
import com.solux.dorandoran.domain.entity.ReviewListEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RecentReviewItem(
    review: ReviewListEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .height(202.dp)
            .clickable { onClick() }
            .border(2.dp, Background01, RoundedCornerShape(15.dp)),
        colors = CardDefaults.cardColors(containerColor = Background01),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            BookImageBox(review = review)

            Spacer(modifier = Modifier.width(20.dp))

            ReviewContent(review = review)
        }
    }
}

@Composable
fun BookImageBox(
    review: ReviewListEntity
) {
    AsyncImage(
        model = review.coverImageUrl,
        contentDescription = null,
        modifier = Modifier
            .size(width = 107.dp, height = 155.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Background03),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        error = painterResource(id = R.drawable.ic_launcher_background)
    )
}

@Composable
private fun ReviewContent(review: ReviewListEntity) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = review.bookTitle,
            style = baseBold,
            color = Neutral60
        )

        RatingStars(rating = review.rating)

        Text(
            text = review.content,
            style = smallRegular,
            color = Neutral70,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.size(25.dp))

        ReviewerInfo(
            nickname = review.nickname,
            createdAt = review.createdAt
        )
    }
}

@Composable
private fun RatingStars(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            val iconRes = if (index < rating) {
                R.drawable.ic_star_filled
            } else {
                R.drawable.ic_star
            }
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun ReviewerInfo(nickname: String, createdAt: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Background03)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = nickname,
                style = smallRegular,
                color = Neutral70
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = formatDateTime(createdAt),
            style = smallRegular,
            color = Neutral70
        )
    }
}

private fun formatDateTime(dateTimeString: String): String {
    return try {
        if (dateTimeString == "방금 전") {
            dateTimeString
        } else {
            val dateTime = LocalDateTime.parse(dateTimeString.substring(0, 19))
            val formatter = DateTimeFormatter.ofPattern("yy-MM-dd")
            dateTime.format(formatter)
        }
    } catch (e: Exception) {
        println(e)
        dateTimeString
    }
}