package com.example.tunewave.ui.components

import android.text.Spanned
import android.text.TextUtils
import android.text.util.Linkify
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.textview.MaterialTextView

@Composable
fun HtmlTextView(
    text: Spanned,
    modifier: Modifier = Modifier,
    textAppearanceResId: Int? = null,
    color: Color = Color.White,
    maxLines: Int = Int.MAX_VALUE
) {
    AndroidView(
        modifier = modifier,
        factory = {
            MaterialTextView(it).apply {
                ellipsize = TextUtils.TruncateAt.END
                textAppearanceResId?.let(::setTextAppearance)
                setTextColor(color.toArgb())
                // links
                autoLinkMask = Linkify.WEB_URLS
                linksClickable = true
                setLinkTextColor(Color.White.toArgb())
                this.maxLines = maxLines
            }
        },
        update = { it.text = text }
    )
}
