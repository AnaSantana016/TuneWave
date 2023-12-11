package com.example.tunewave.data.remote.response

import com.example.tunewave.domain.SearchResult

data class NewReleasesResponse(val albums: Albums) {

    data class Albums(val items: List<AlbumMetadataResponse>)
}

fun NewReleasesResponse.toAlbumSearchResultList(): List<SearchResult.AlbumSearchResult> =
    this.albums.items.map { it.toAlbumSearchResult() }