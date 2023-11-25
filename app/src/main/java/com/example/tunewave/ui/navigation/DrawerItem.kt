package com.example.tunewave.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(
    val icon: ImageVector,
    val text: String
) {
    PROFILE(Icons.Default.Info, "Profile"),
    HOME(Icons.Default.Home, "Home"),
    SETTINGS(Icons.Default.Settings, "Settings"),
    MY_FAVOURITES(Icons.Default.Favorite, "My Favourites"),
    MY_LIKES(Icons.Default.ThumbUp, "My Likes"),
    DISCOVER_MUSIC(Icons.Default.Search, "Discover Music"),
    SIGN_OUT(Icons.Default.ExitToApp, "Sign Out")
}