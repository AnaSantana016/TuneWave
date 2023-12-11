package com.example.tunewave.data.remote.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PlaylistItemsResponse(val items: List<TrackResponseWithAlbumMetadataWrapper>) {
    data class TrackResponseWithAlbumMetadataWrapper(@JsonProperty("track") val track: TrackResponseWithAlbumMetadata)
}

