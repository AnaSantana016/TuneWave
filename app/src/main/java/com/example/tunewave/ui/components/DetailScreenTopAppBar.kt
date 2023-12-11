package com.example.tunewave.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier.DynamicBackgroundResource
import com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier.DynamicBackgroundStyle
import com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier.dynamicBackground
import com.example.tunewave.ui.theme.MusifyTheme
import com.example.tunewave.R

@Composable
fun DetailScreenTopAppBar(
    title: String,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    dynamicBackgroundResource: DynamicBackgroundResource = DynamicBackgroundResource.Empty
) {
    val dynamicBackgroundStyle = remember {
        DynamicBackgroundStyle.Filled(scrimColor = Color.Black.copy(alpha = 0.3f))
    }

    TopAppBar(
        modifier = Modifier
            .dynamicBackground(dynamicBackgroundResource, dynamicBackgroundStyle)
            .then(modifier)
            .clickable(onClick = onClick),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.CenterVertically)
                .offset(y = 1.dp),
            onClick = onBackButtonClicked
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_chevron_left_24),
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun DetailScreenTopAppBarPreview() {
    MusifyTheme {
        DetailScreenTopAppBar(
            title = "Title",
            onBackButtonClicked = {},
            dynamicBackgroundResource = DynamicBackgroundResource.Empty
        )
    }
}