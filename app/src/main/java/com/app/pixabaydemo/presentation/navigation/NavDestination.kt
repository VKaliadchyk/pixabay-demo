package com.app.pixabaydemo.presentation.navigation



sealed class NavDestination(val route: String) {

    object Initial : NavDestination(INITIAL_ROUTE)

    object GalleryScreen : NavDestination(GALLERY_SCREEN_SCREEN_ROUTE)

    class ImageDetailScreen(image: String) : NavDestination(IMAGE_DETAIL_SCREEN_ROUTE) {
        val routeWithArgument = "$route/$image"
        companion object {
            const val ARGUMENT_KEY = "image"
            const val argumentRoute = "$IMAGE_DETAIL_SCREEN_ROUTE/{$ARGUMENT_KEY}"
        }
    }

    companion object {
        private const val INITIAL_ROUTE = "Initial"
        private const val GALLERY_SCREEN_SCREEN_ROUTE = "GalleryScreen"
        private const val IMAGE_DETAIL_SCREEN_ROUTE = "ImageDetailScreen"
    }
}
