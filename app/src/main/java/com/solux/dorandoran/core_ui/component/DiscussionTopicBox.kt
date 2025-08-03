package com.solux.dorandoran.core_ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.smallRegular02
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.entity.DiscussPageEntity

@Composable
fun DiscussionTopicBox(
    discussion: DiscussPageEntity,
    argument: DiscussCommentEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape= RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5FFF6)),
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Button02, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF5FFF6))
                .padding(horizontal = 100.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_discussionscreen_chatbubble),
            contentDescription = "댓글 추가",
            modifier = Modifier.size(20.dp),
                tint = Button02
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = discussion.content,
                style = baseBold,
                color = Neutral60
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = argument.content,
                style = smallRegular02,
                color = Neutral60,
                maxLines = 4
            )
        }
    }
}