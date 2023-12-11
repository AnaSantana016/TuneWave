package com.example.tunewave.ui.screens.searchscreen

import androidx.compose.ui.graphics.Color
import com.example.tunewave.R
import com.example.tunewave.domain.Genre

fun Genre.GenreType.getAssociatedImageResource(): Int = when (this) {
    Genre.GenreType.AMBIENT -> R.drawable.genre_img_ambient
    Genre.GenreType.CHILL -> R.drawable.genre_img_chill
    Genre.GenreType.CLASSICAL -> R.drawable.genre_img_classical
    Genre.GenreType.DANCE -> R.drawable.genre_img_dance
    Genre.GenreType.ELECTRONIC -> R.drawable.genre_img_electronic
    Genre.GenreType.METAL -> R.drawable.genre_img_metal
    Genre.GenreType.RAINY_DAY -> R.drawable.genre_img_rainy_day
    Genre.GenreType.ROCK -> R.drawable.genre_img_rock
    Genre.GenreType.PIANO -> R.drawable.genre_img_piano
    Genre.GenreType.POP -> R.drawable.genre_img_pop
    Genre.GenreType.SLEEP -> R.drawable.genre_img_sleep
}

fun Genre.GenreType.getAssociatedBackgroundColor() = when (this) {
    Genre.GenreType.AMBIENT -> Color(0, 48, 72)
    Genre.GenreType.CHILL -> Color(71, 126, 149)
    Genre.GenreType.CLASSICAL -> Color(141, 103, 171)
    Genre.GenreType.DANCE -> Color(140, 25, 50)
    Genre.GenreType.ELECTRONIC -> Color(186, 93, 7)
    Genre.GenreType.METAL -> Color(119, 119, 119)
    Genre.GenreType.RAINY_DAY -> Color(144, 168, 192)
    Genre.GenreType.ROCK -> Color(230, 30, 50)
    Genre.GenreType.PIANO -> Color(71, 125, 149)
    Genre.GenreType.POP -> Color(141, 103, 171)
    Genre.GenreType.SLEEP -> Color(30, 50, 100)
}