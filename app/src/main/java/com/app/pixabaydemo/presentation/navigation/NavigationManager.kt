package com.app.pixabaydemo.presentation.navigation

import kotlinx.coroutines.flow.StateFlow

interface NavigationManager {

    val destination: StateFlow<NavDestination>

    fun navigate(destination: NavDestination)
}
