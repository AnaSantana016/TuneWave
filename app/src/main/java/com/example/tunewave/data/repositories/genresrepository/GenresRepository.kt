package com.example.tunewave.data.repositories.genresrepository

import com.example.tunewave.domain.Genre

interface GenresRepository {
    fun fetchAvailableGenres(): List<Genre>
}