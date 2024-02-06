package com.app.pixabaydemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.pixabaydemo.presentation.ui.screen.gallery.GalleryScreen
import com.app.pixabaydemo.presentation.ui.screen.gallery.GalleryViewModel
import com.app.pixabaydemo.presentation.ui.theme.PixabayDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: GalleryViewModel = hiltViewModel()
            val imageList by viewModel.imageListState.collectAsState()
            val searchQueryValue by viewModel.searchQueryValueState.collectAsState()
            val isDetailsConfirmationDialogVisible by viewModel.isDetailsConfirmationDialogVisibleState.collectAsState()
            val selectedImage by viewModel.selectedImage.collectAsState()

            PixabayDemoAppTheme {
                Surface {
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
            }
        }
    }
}
