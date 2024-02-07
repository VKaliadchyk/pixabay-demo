package com.app.pixabaydemo.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManagerImpl @Inject constructor() : NavigationManager {

    private val _destination = MutableStateFlow<NavDestination>(NavDestination.Initial)
    override val destination = _destination.asStateFlow()

    override fun navigate(destination: NavDestination) {
        _destination.update { destination }
    }
}
