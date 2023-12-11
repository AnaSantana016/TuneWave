package com.example.tunewave.data.paging

import com.example.tunewave.data.remote.musicservice.SearchQueryType
import com.example.tunewave.data.remote.musicservice.SpotifyService
import com.example.tunewave.data.remote.response.SearchResultsResponse
import com.example.tunewave.data.repositories.tokenrepository.TokenRepository
import com.example.tunewave.domain.SearchResult
import retrofit2.HttpException
import java.io.IOException

class SpotifySearchPagingSource<T : SearchResult>(
    searchQuery: String,
    countryCode: String,
    searchQueryType: SearchQueryType,
    tokenRepository: TokenRepository,
    spotifyService: SpotifyService,
    resultsBlock: (SearchResultsResponse) -> List<T>
) : SpotifyPagingSource<T>(
    loadBlock = { limit: Int, offset: Int ->
        try {
            val searchResultsResponse = spotifyService.search(
                searchQuery = searchQuery,
                market = countryCode,
                token = tokenRepository.getValidBearerToken(),
                limit = limit,
                offset = offset,
                type = searchQueryType.value
            )
            SpotifyLoadResult.PageData(resultsBlock(searchResultsResponse))
        } catch (httpException: HttpException) {
            SpotifyLoadResult.Error(httpException)
        } catch (ioException: IOException) {
            // indicates that there was some network error
            SpotifyLoadResult.Error(ioException)
        }
    }
)

sealed class SpotifyLoadResult<Value : Any> {

    data class PageData<Value : Any>(val data: List<Value>) : SpotifyLoadResult<Value>()

    data class Error<Value : Any>(val throwable: Throwable) : SpotifyLoadResult<Value>()
}
