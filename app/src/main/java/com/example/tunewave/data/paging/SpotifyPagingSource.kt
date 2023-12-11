package com.example.tunewave.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

sealed class SpotifyPagingSource<V : Any>(
    private val loadBlock: suspend (
        limit: Int,
        offset: Int
    ) -> SpotifyLoadResult<V>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? = state.anchorPosition
        ?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val pageNumber = params.key ?: 0
        val spotifyLoadResult = loadBlock(
            params.loadSize.coerceAtMost(50), // Spotify API doesn't allow 'limit' to exceed 50
            params.loadSize * pageNumber
        )
        return when (spotifyLoadResult) {
            is SpotifyLoadResult.Error -> LoadResult.Error(spotifyLoadResult.throwable)
            is SpotifyLoadResult.PageData -> LoadResult.Page(
                data = spotifyLoadResult.data,
                prevKey = if (pageNumber == 0) null else pageNumber - 1,
                nextKey = if (spotifyLoadResult.data.isEmpty()) null else pageNumber + 1,
            )
        }

    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }


}