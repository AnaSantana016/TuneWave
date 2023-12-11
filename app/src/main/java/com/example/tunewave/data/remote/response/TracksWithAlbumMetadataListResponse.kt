package com.example.tunewave.data.remote.response

import com.fasterxml.jackson.annotation.JsonProperty

data class TracksWithAlbumMetadataListResponse(@JsonProperty("tracks") val value: List<TrackResponseWithAlbumMetadata>)