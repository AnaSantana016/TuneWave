package com.example.tunewave.data.remote.response

data class BrowseCategoriesResponse(val categories: BrowseCategoryItemsResponse) {

    data class BrowseCategoryItemsResponse(val items: List<BrowseCategoryResponse>)

    data class BrowseCategoryResponse(
        val id: String,
        val name: String
    )
}
