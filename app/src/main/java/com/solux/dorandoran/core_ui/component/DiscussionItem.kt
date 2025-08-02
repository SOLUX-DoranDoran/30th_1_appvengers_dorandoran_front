package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import com.solux.dorandoran.core_ui.theme.Neutral80
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.smallBold
import com.solux.dorandoran.core_ui.theme.smallRegular02
import com.solux.dorandoran.domain.entity.DiscussionEntity

@Composable
fun DiscussionItem(
    discussion: DiscussionEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .widthIn(max = 363.dp)
            .heightIn(max = 202.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Background01),
        border = BorderStroke(width = 2.dp, color = Background01),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 21.dp)
        ) {
            BookCoverImage(
                imageUrl = discussion.imageUrl,
                modifier = Modifier
                    .weight(0.36f)
                    .aspectRatio(0.71f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            DiscussionContent(
                discussion = discussion,
                modifier = Modifier.weight(0.64f)
            )
        }
    }
}

@Composable
private fun BookCoverImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    if (imageUrl.isNotBlank()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Background03),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background)
        )
    } else {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Background03)
        )
    }
}

@Composable
private fun DiscussionContent(
    discussion: DiscussionEntity,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = discussion.title,
            style = baseBold,
            color = Neutral60,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = discussion.bookTitle,
            style = smallBold,
            color = Neutral70,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth().weight(0.8f)
        )

        UserInfo(
            user = discussion.user,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun UserInfo(
    user: com.solux.dorandoran.domain.entity.UserEntity?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        UserProfileImage(profileImageUrl = user?.profileImage)

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "${user?.nickname ?: "익명"} 님",
            style = smallRegular02,
            color = Neutral70,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun UserProfileImage(
    profileImageUrl: String?,
    modifier: Modifier = Modifier
) {
    if (!profileImageUrl.isNullOrBlank()) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            modifier = modifier
                .size(36.dp)
                .border(
                    width = 0.68.dp,
                    color = Neutral80,
                    shape = CircleShape
                )
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background)
        )
    } else {
        Box(
            modifier = modifier
                .size(36.dp)
                .border(
                    width = 0.68.dp,
                    color = Neutral80,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(Background03)
        )
    }
}