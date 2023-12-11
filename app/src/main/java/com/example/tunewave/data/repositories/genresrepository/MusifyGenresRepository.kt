package com.example.tunewave.data.repositories.genresrepository

import com.example.tunewave.data.remote.musicservice.SupportedSpotifyGenres
import com.example.tunewave.data.remote.musicservice.toGenre
import com.example.tunewave.domain.Genre
import javax.inject.Inject

class MusifyGenresRepository @Inject constructor() : GenresRepository {
    override fun fetchAvailableGenres(): List<Genre> = SupportedSpotifyGenres.values().map {
        it.toGenre()
    }
}