package com.app.pixabaydemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.pixabaydemo.presentation.ui.screen.gallery.GalleryScreen
import com.app.pixabaydemo.presentation.ui.screen.gallery.GalleryViewModel
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.ImageDetailsScreen
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.ImageDetailsViewModel
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestination.GalleryScreen.route) {
        composable(route = NavDestination.GalleryScreen.route) {
            val viewModel: GalleryViewModel = hiltViewModel()
            val imageList by viewModel.imageListState.collectAsState()
            val searchQueryValue by viewModel.searchQueryValueState.collectAsState()
            val isDetailsConfirmationDialogVisible by viewModel.isDetailsConfirmationDialogVisibleState.collectAsState()
            val selectedImage by viewModel.selectedImage.collectAsState()

            GalleryScreen(
                imageList = imageList,
                searchQueryValue = searchQueryValue,
                isDetailsConfirmationDialogVisible = isDetailsConfirmationDialogVisible,
                selectedImage = selectedImage,
                onSearchQueryChange = { newValue ->
                    viewModel.onSearchQueryChange(newValue)
                },
                onListItemClick = { imageData ->
                    viewModel.onListItemClick(imageData)
                },
                onDetailsConfirmationDialogConfirmClick = { imageData ->
                    viewModel.onConfirmDetailsConfirmationDialog(imageData)
                },
                onDialogDismissClick = {
                    viewModel.dismissDialogs()
                }
            )
        }
        composable(
            route = NavDestination.ImageDetailScreen.destinationRoute,
            arguments = listOf(
                navArgument(NavDestination.ImageDetailScreen.ARGUMENT_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: ImageDetailsViewModel = hiltViewModel()
            ImageDetailsScreen(detailedImageData = DetailedImageData.defaultValue)
        }
    }
}
