package com.example.tunewave.ui.components

import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage

@ExperimentalMaterialApi
@Composable
fun HomeFeedCard(
    imageUrlString: String,
    caption: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumContentAlpha = ContentAlpha.medium
    Card(
        modifier = Modifier
            .widthIn(min = 160.dp, max = 160.dp)
            .height(IntrinsicSize.Min)
            .then(modifier),
        backgroundColor = Color.Transparent,
        onClick = onClick,
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                model = imageUrlString,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            AndroidView(factory = {
                TextView(it).apply {
                    maxLines = 2
                    minLines = maxLines
                    alpha = mediumContentAlpha
                    text = caption
                    ellipsize = TextUtils.TruncateAt.END
                    setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Caption)
                    setTypeface(typeface, Typeface.NORMAL)
                }
            })
        }
    }
}