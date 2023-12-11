package com.example.tunewave.data.remote.musicservice

enum class SearchQueryType(val value: String) {
    ALBUM("album"),
    ARTIST("artist"),
    PLAYLIST("playlist"),
    TRACK("track"),
    SHOW("show"),
    EPISODE("episode")
}

fun buildSearchQueryWithTypes(vararg types: SearchQueryType): String {
    if (types.isEmpty()) throw IllegalArgumentException("The list cannot be empty")
    var query = types.first().value
    if (types.size == 1) return query

    for (i in 1..types.lastIndex) {
        query += ",${types[i].value}"
    }
    return query
}