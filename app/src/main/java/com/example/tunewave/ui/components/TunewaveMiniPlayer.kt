package com.example.tunewave.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tunewave.R
import com.example.tunewave.domain.Streamable
import com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier.DynamicBackgroundResource
import com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier.DynamicBackgroundStyle
import com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier.dynamicBackground

object MusifyMiniPlayerConstants {
    val miniPlayerHeight = 60.dp
}


@Composable
fun MusifyMiniPlayer(
    streamable: Streamable,
    isPlaybackPaused: Boolean,
    modifier: Modifier = Modifier,
    onPlayButtonClicked: () -> Unit,
    onPauseButtonClicked: () -> Unit
) {
    var isThumbnailImageLoading by remember { mutableStateOf(false) }
    val dynamicBackgroundResource = remember {
        DynamicBackgroundResource.FromImageUrl(streamable.streamInfo.imageUrl)
    }
    val dynamicBackgroundStyle = remember { DynamicBackgroundStyle.Filled() }
    Row(
        modifier = modifier
            .height(MusifyMiniPlayerConstants.miniPlayerHeight)
            .clip(RoundedCornerShape(8.dp))
            .dynamicBackground(dynamicBackgroundResource, dynamicBackgroundStyle),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImageWithPlaceholder(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .aspectRatio(1f),
            model = streamable.streamInfo.imageUrl,
            contentDescription = null,
            onImageLoadingFinished = { isThumbnailImageLoading = false },
            isLoadingPlaceholderVisible = isThumbnailImageLoading,
            onImageLoading = { isThumbnailImageLoading = true },
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = streamable.streamInfo.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = streamable.streamInfo.subtitle,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_available_devices),
                contentDescription = null
            )
        }
        IconButton(onClick = {
            if (isPlaybackPaused) {
                onPlayButtonClicked()
            } else {
                onPauseButtonClicked()
            }
        }) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .aspectRatio(1f),
                painter = if (isPlaybackPaused) painterResource(R.drawable.ic_play_arrow_24)
                else painterResource(R.drawable.ic_pause_24),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}

