package com.example.tunewave.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun MusifyFilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        border = if (isSelected) BorderStroke(1.dp, MaterialTheme.colors.primary)
        else ButtonDefaults.outlinedBorder,
        colors = ChipDefaults.filterChipColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.7f)
            else MaterialTheme.colors.surface
        ),
        content = { Text(text = text, color = Color.White) }
    )
}