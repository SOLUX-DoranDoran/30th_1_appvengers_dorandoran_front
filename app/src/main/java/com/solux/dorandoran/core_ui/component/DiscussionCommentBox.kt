package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral70
import com.solux.dorandoran.core_ui.theme.baseRegular
import com.solux.dorandoran.core_ui.theme.smallRegular
import androidx.compose.ui.graphics.Path
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Button02
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background03
import com.solux.dorandoran.domain.entity.DiscussCommentEntity
import com.solux.dorandoran.domain.entity.DiscussPageEntity

@Composable
fun ArgumentBubble(
    modifier: Modifier = Modifier,
    isOddPosition: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .drawBehind {
                drawBubbleShape(
                    size = size,
                    color = Background01,
                    isRightTail = isOddPosition
                )
            }
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        content()
    }
}

private fun DrawScope.drawBubbleShape(
    size: Size,
    color: Color,
    isRightTail: Boolean
) {
    val cornerRadius = 15.dp.toPx()
    val tailWidth = 12.dp.toPx()
    val tailHeight = 8.dp.toPx()
    val tailOffset = 20.dp.toPx()
    val bubbleHeight = size.height - tailHeight

    val path = Path().apply {
        if (isRightTail) {
            val bubbleWidth = size.width - tailWidth
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, bubbleWidth, bubbleHeight),
                    cornerRadius = CornerRadius(cornerRadius)
                )
            )
            val tailStartX = bubbleWidth - tailOffset
            val tailStartY = bubbleHeight
            moveTo(tailStartX, tailStartY)
            cubicTo(
                tailStartX + 6f, tailStartY + 3f,
                tailStartX + 8f, tailStartY + 6f,
                tailStartX + 6f, tailStartY + 8f
            )
            cubicTo(
                tailStartX + 4f, tailStartY + 6f,
                tailStartX + 2f, tailStartY + 3f,
                tailStartX, tailStartY
            )
        } else {
            val bubbleStartX = tailWidth
            addRoundRect(
                RoundRect(
                    rect = Rect(bubbleStartX, 0f, size.width, bubbleHeight),
                    cornerRadius = CornerRadius(cornerRadius)
                )
            )
            val tailStartX = bubbleStartX + tailOffset
            val tailStartY = bubbleHeight
            moveTo(tailStartX, tailStartY)
            cubicTo(
                tailStartX - 6f, tailStartY + 3f,
                tailStartX - 8f, tailStartY + 6f,
                tailStartX - 6f, tailStartY + 8f
            )
            cubicTo(
                tailStartX - 4f, tailStartY + 6f,
                tailStartX - 2f, tailStartY + 3f,
                tailStartX, tailStartY
            )
        }
    }

    drawIntoCanvas {
        it.drawPath(path, Paint().apply {
            this.color = color
            this.isAntiAlias = true
        })
    }
}

@Composable
fun DiscussionCommentBox(
    discussion: DiscussPageEntity,
    argument: DiscussCommentEntity,
    comments: List<DiscussCommentEntity>, // 수정: List로 변경
    onAddComment: (Int) -> Unit,
    isInputVisible: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ArgumentBubble(
                isOddPosition = true,
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row {
                        // 프로필 이미지
                        Box(
                            modifier = Modifier
                                .size(37.dp)
                                .clip(CircleShape)
                                .background(Background03)
                                .border(0.5.dp, Neutral70, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = argument.content,
                                style = baseRegular
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = argument.memberNickname,
                                    style = smallRegular,
                                    color = Neutral60
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .height(15.dp)
                                        .background(Neutral70)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = argument.createdAt,
                                    style = smallRegular,
                                    color = Neutral70
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    // 댓글들 표시
                    if (comments.isNotEmpty()) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            comments.forEachIndexed { index, comment ->
                                CommentItem(
                                    comment = comment,
                                    modifier = modifier
                                )
                                if (index < comments.size - 1) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
            // 댓글 추가 버튼
            FloatingActionButton(
                onClick = { onAddComment(argument.id) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 8.dp)
                    .size(36.dp)
                    .clip(CircleShape),
                containerColor = Button02,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_discussionscreen_chatbubble),
                    contentDescription = "댓글 추가",
                    modifier = Modifier.size(20.dp),
                    tint = Background01
                )
            }
        }
    }

    if (isInputVisible) {
        var inputText by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .focusRequester(focusRequester),
            placeholder = { Text("댓글을 입력하세요...") },
            singleLine = false,
            maxLines = 5
        )
    }
}

@Composable
private fun CommentItem(
    comment: DiscussCommentEntity,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 8.dp)
    ) {
        // 댓글 인디케이터
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Reply icon",
            tint = Button02,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 프로필 이미지
                Box(
                    modifier = Modifier
                        .size(37.dp)
                        .clip(CircleShape)
                        .background(Background03)
                        .border(0.5.dp, Neutral70, CircleShape)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = comment.content,
                        style = baseRegular
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = comment.memberNickname,
                            style = smallRegular,
                            color = Neutral60
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(15.dp)
                                .background(Neutral70)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = comment.createdAt,
                            style = smallRegular,
                            color = Neutral70
                        )
                    }
                }
            }
        }
    }
}
