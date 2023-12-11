package com.example.tunewave.ui.navigation

import com.example.tunewave.R

sealed class TunewaveBottomNavigationDestinations(
    val route: String,
    val label: String,
    val outlinedIconVariantResourceId: Int,
    val filledIconVariantResourceId: Int
) {
    object Home : TunewaveBottomNavigationDestinations(
        route = "com.example.musify.ui.navigation.bottom.home",
        label = "Home",
        outlinedIconVariantResourceId = R.drawable.ic_outline_home_24,
        filledIconVariantResourceId = R.drawable.ic_filled_home_24
    )

    object Search : TunewaveBottomNavigationDestinations(
        route = "com.example.musify.ui.navigation.bottom.search",
        label = "Search",
        outlinedIconVariantResourceId = R.drawable.ic_outline_search_24,
        filledIconVariantResourceId = R.drawable.ic_outline_search_24
    )

    object Profile : TunewaveBottomNavigationDestinations(
        route = "com.example.tunewave.ui.navigation.bottom.profile",
        label = "Profile",
        outlinedIconVariantResourceId = R.drawable.ic_outline_account_circle_24, // Asegúrate de que el archivo sea .xml
        filledIconVariantResourceId = R.drawable.ic_outline_account_circle_24
    )
}
