package com.solux.dorandoran.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.solux.dorandoran.R
import com.solux.dorandoran.core_ui.theme.Background01
import com.solux.dorandoran.core_ui.theme.Button02
import com.solux.dorandoran.core_ui.theme.Neutral60
import com.solux.dorandoran.core_ui.theme.Neutral80
import com.solux.dorandoran.core_ui.theme.baseBold
import com.solux.dorandoran.core_ui.theme.baseRegular

@Composable
fun ReviewWriteSection(
    rating: Int,
    content: String,
    onRatingChange: (Int) -> Unit,
    onContentChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "별점",
            style = baseBold,
            color = Neutral60
        )

        Spacer(modifier = Modifier.height(12.dp))

        InteractiveRatingStars(
            rating = rating,
            onRatingChange = onRatingChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "리뷰",
            style = baseBold,
            color = Neutral60
        )

        Spacer(modifier = Modifier.height(12.dp))

        ReviewContentTextField(
            value = content,
            onValueChange = onContentChange,
            placeholder = "리뷰 내용을 남겨 주세요!"
        )

        Spacer(modifier = Modifier.height(32.dp))

        SubmitButton(
            onClick = onSubmit,
            enabled = rating > 0 && content.isNotBlank()
        )
    }
}

@Composable
private fun InteractiveRatingStars(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val starRating = index + 1
            Image(
                painter = painterResource(
                    id = if (starRating <= rating) {
                        R.drawable.ic_star_filled
                    } else {
                        R.drawable.ic_star
                    }
                ),
                contentDescription = "별점 $starRating",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChange(starRating) }
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = rating.toString(),
            style = baseBold,
            color = Neutral60
        )
    }
}

@Composable
private fun ReviewContentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp),
        placeholder = {
            Text(
                text = placeholder,
                style = baseRegular,
                color = Neutral80
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Button02,
            unfocusedBorderColor = Neutral80,
            focusedContainerColor = Background01,
            unfocusedContainerColor = Background01
        ),
        shape = RoundedCornerShape(15.dp),
        textStyle = baseRegular.copy(color = Neutral60),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Default
        )
    )
}

@Composable
private fun SubmitButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Button02,
            contentColor = Background01,
            disabledContainerColor = Neutral80,
            disabledContentColor = Background01
        )
    ) {
        Text(
            text = "등록하기",
            style = baseBold
        )
    }
}