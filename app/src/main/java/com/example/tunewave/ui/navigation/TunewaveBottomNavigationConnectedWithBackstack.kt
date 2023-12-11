package com.example.tunewave.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tunewave.ui.components.MusifyBottomNavigation

@Composable
fun MusifyBottomNavigationConnectedWithBackStack(
    navController: NavHostController,
    navigationItems: List<TunewaveBottomNavigationDestinations>,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    var previousValidBottomNavigationDestination by remember {
        mutableStateOf<TunewaveBottomNavigationDestinations>(TunewaveBottomNavigationDestinations.Home)
    }
    val currentlySelectedItem by remember {
        derivedStateOf {

            currentBackStackEntry?.let {
                val route = if (it.isInNestedNavGraph) it.destination.parent?.route
                else it.destination.route
                previousValidBottomNavigationDestination = when (route) {
                    TunewaveBottomNavigationDestinations.Home.route -> TunewaveBottomNavigationDestinations.Home
                    TunewaveBottomNavigationDestinations.Search.route -> TunewaveBottomNavigationDestinations.Search
                    TunewaveBottomNavigationDestinations.Profile.route -> TunewaveBottomNavigationDestinations.Profile
                    else -> previousValidBottomNavigationDestination
                }
                previousValidBottomNavigationDestination
            } ?: previousValidBottomNavigationDestination
        }
    }
    MusifyBottomNavigation(
        modifier = modifier,
        navigationItems = navigationItems,
        currentlySelectedItem = currentlySelectedItem,
        onItemClick = { bottomNavigationDestination ->
            if (
                bottomNavigationDestination == currentlySelectedItem &&
                navController.currentDestinationRoute !=
                navController.parentOfCurrentDestination?.startDestinationRoute
            ) {
                navController.popBackStack()
            } else {
                navController.navigate(bottomNavigationDestination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {

                        saveState = true
                    }
                    launchSingleTop = true

                    restoreState = true
                }
            }
        }
    )
}

private val NavBackStackEntry.isInNestedNavGraph get() = this.destination.parent?.parent != null

private val NavHostController.currentDestinationRoute: String? get() = this.currentDestination?.route

private val NavHostController.parentOfCurrentDestination: NavGraph? get() = this.currentDestination?.parent