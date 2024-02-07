package com.app.pixabaydemo.presentation.util.extension

import androidx.navigation.NavHostController
import com.app.pixabaydemo.presentation.navigation.NavDestination

fun NavHostController.navigate(destination: NavDestination) {
    if (destination != NavDestination.Initial) {
        when (destination) {
            is NavDestination.ImageDetailScreen -> navigate(destination.routeWithArgument)
            else -> navigate(destination.route)
        }
    }
}
