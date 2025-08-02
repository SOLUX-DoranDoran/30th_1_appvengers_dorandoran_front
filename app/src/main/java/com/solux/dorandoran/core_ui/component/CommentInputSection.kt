package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral80
import com.solux.dorandoran.core_ui.theme.baseRegular

@Composable
fun CommentInputSection(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by remember { mutableStateOf(false) }
    val topRoundedCornerShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = topRoundedCornerShape,
                clip = false
            )
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(Background01)
            .padding(16.dp)
    ) {
        CommentInputHeader(onCancel = onCancel)

        Spacer(modifier = Modifier.height(12.dp))

        CommentInputRow(
            value = value,
            onValueChange = onValueChange,
            onSubmit = {
                if (value.isNotBlank()) {
                    onSubmit()
                    keyboardController?.hide()
                }
            },
            onFocusChanged = { focused -> isFocused = focused }
        )
    }
}

@Composable
private fun CommentInputHeader(
    onCancel: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "취소",
            style = baseRegular,
            color = Neutral80,
            modifier = Modifier.clickable { onCancel() }
        )
    }
}

@Composable
private fun CommentInputRow(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        CommentTextField(
            value = value,
            onValueChange = onValueChange,
            onSubmit = onSubmit,
            onFocusChanged = onFocusChanged,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        SendButton(
            onClick = onSubmit,
            enabled = value.isNotBlank()
        )
    }
}

@Composable
private fun CommentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .heightIn(min = 40.dp, max = 120.dp)
            .padding(12.dp)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        textStyle = baseRegular.copy(color = Neutral60),
        cursorBrush = SolidColor(Button02),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = "| 코멘트를 입력하세요.",
                    style = baseRegular,
                    color = Neutral80
                )
            }
            innerTextField()
        }
    )
}

@Composable
private fun SendButton(
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(48.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Button02,
            contentColor = Background01,
            disabledContainerColor = Neutral80
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_reviewscreen_send),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
    }
}