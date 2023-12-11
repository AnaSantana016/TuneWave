package com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext

sealed interface DynamicBackgroundResource {
    data class FromImageUrl(val url: String) : DynamicBackgroundResource
    object Empty : DynamicBackgroundResource
}

sealed interface DynamicBackgroundStyle {

    data class Gradient(val fraction: Float = 1f) : DynamicBackgroundStyle

    data class Filled(val scrimColor: Color? = null) : DynamicBackgroundStyle
}

fun Modifier.dynamicBackground(
    dynamicBackgroundResource: DynamicBackgroundResource = DynamicBackgroundResource.Empty,
    dynamicBackgroundStyle: DynamicBackgroundStyle = DynamicBackgroundStyle.Gradient()
) = composed {
    val context = LocalContext.current
    val themeManager = LocalDynamicThemeManager.current
    val defaultBackgroundColor = MaterialTheme.colors.background
    var backgroundColor by remember { mutableStateOf(defaultBackgroundColor) }
    val animatedBackgroundColor by animateColorAsState(targetValue = backgroundColor)
    val backgroundGradientColors = remember(animatedBackgroundColor) {
        listOf(
            animatedBackgroundColor,
            Color(0xFF121212),
        )
    }
    LaunchedEffect(dynamicBackgroundResource) {
        val newBackgroundColor = when (dynamicBackgroundResource) {
            DynamicBackgroundResource.Empty -> defaultBackgroundColor
            is DynamicBackgroundResource.FromImageUrl -> themeManager
                .getBackgroundColorForImageFromUrl(dynamicBackgroundResource.url, context)
                ?: return@LaunchedEffect
        }
        backgroundColor = newBackgroundColor
    }

    Modifier.drawBehind {
        // skip composition, measurement and layout phase
        // and just change the color in the drawing phase.
        when (dynamicBackgroundStyle) {
            is DynamicBackgroundStyle.Filled -> drawRectFilledWithColor(
                color = animatedBackgroundColor,
                scrimColor = dynamicBackgroundStyle.scrimColor
            )
            is DynamicBackgroundStyle.Gradient -> {
                drawRectWithGradient(backgroundGradientColors, dynamicBackgroundStyle.fraction)
            }
        }
    }
}


private fun DrawScope.drawRectFilledWithColor(
    color: Color,
    scrimColor: Color? = null
) {
    drawRect(
        color = color,
        size = size
    )
    if (scrimColor != null) {
        drawRect(
            color = scrimColor,
            size = size
        )
    }
}

private fun DrawScope.drawRectWithGradient(backgroundGradientColors: List<Color>, fraction: Float) {
    drawRect(
        brush = Brush.verticalGradient(
            colors = backgroundGradientColors,
            endY = size.height * fraction
        ),
        size = size
    )
}