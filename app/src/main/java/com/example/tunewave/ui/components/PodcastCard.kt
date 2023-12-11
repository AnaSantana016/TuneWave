package com.example.tunewave.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun PodcastCard(
    podcastArtUrlString: String,
    name: String,
    nameOfPublisher: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoadingPlaceholderVisible by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(IntrinsicSize.Min)
            .then(modifier),
        backgroundColor = Color.Transparent,
        onClick = onClick,
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImageWithPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp)),
                model = podcastArtUrlString,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                isLoadingPlaceholderVisible = isLoadingPlaceholderVisible,
                onImageLoading = { isLoadingPlaceholderVisible = true },
                onImageLoadingFinished = { isLoadingPlaceholderVisible = false }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.subtitle2,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = nameOfPublisher,
                style = MaterialTheme.typography.subtitle2.copy(
                    Color.White.copy(alpha = ContentAlpha.medium)
                ),
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}