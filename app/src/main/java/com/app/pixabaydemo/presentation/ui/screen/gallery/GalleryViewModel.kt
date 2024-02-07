package com.app.pixabaydemo.presentation.ui.screen.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.entity.SearchParameters
import com.app.pixabaydemo.domain.usecase.GetImageUseCase
import com.app.pixabaydemo.presentation.navigation.NavDestination
import com.app.pixabaydemo.presentation.navigation.NavigationManager
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.GalleryScreenInitialValues
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val navigationManager: NavigationManager,
    private val converter: Converter<ImageData, ImageListItemData>,
    private val gson: Gson,
    initialValues: GalleryScreenInitialValues
) : ViewModel() {

    private val LOG_TAG = this.javaClass.name

    private val imageDataMap = mutableMapOf<Int, ImageData>()

    private val _errorMessageState: MutableStateFlow<String?> = MutableStateFlow(initialValues.errorMessage)
    val errorMessageState = _errorMessageState.asStateFlow()

    private val _selectedImageState: MutableStateFlow<ImageListItemData?> = MutableStateFlow(initialValues.selectedImage)
    val selectedImageState = _selectedImageState.asStateFlow()

    private val _isDetailsConfirmationDialogVisibleState = MutableStateFlow(initialValues.isDetailsConfirmationDialogVisible)
    val isDetailsConfirmationDialogVisibleState = _isDetailsConfirmationDialogVisibleState.asStateFlow()

    private val _imageListState = MutableStateFlow(initialValues.imageList)
    val imageListState = _imageListState.asStateFlow()

    private val _searchQueryValueState = MutableStateFlow(initialValues.searchBarValue)
    val searchQueryValueState = _searchQueryValueState.asStateFlow()


    fun onListItemClick(imageListItemData: ImageListItemData) {
        _isDetailsConfirmationDialogVisibleState.update { true }
        _selectedImageState.update { imageListItemData }
    }

    fun onSearchQueryChange(query: String) {
        _searchQueryValueState.update { query }
    }

    fun dismissDialogs() {
        hideAllDialogs()
    }

    fun onConfirmDetailsConfirmationDialog(imageListItemData: ImageListItemData) {
        hideAllDialogs()

        try {
            val imageData = imageDataMap[imageListItemData.id]!!
            val imageDataJson = gson.toJson(imageData)
            navigationManager.navigate(NavDestination.ImageDetailScreen(imageDataJson))
        } catch (npe: NullPointerException) {
            Log.e(LOG_TAG, npe.message, npe)
            //TODO show error dialog to the user as well
        }
    }

    private suspend fun handleSearchRequest(searchQuery: String) {
        val resource = getImageUseCase.execute(SearchParameters(searchQuery))

        when (resource) {
            is Resource.Success -> {
                handleSuccessfulRequest(resource.data)
            }

            is Resource.Failure -> {
                handleFailedRequest(resource.message)
            }
        }
    }

    private fun handleSuccessfulRequest(imageDataList: List<ImageData>) {
        updateImageMap(imageDataList)

        val imageListItemDataDataList = imageDataList
            .map { imageData -> imageData.toImageListItemData() }

        _errorMessageState.update { null }
        _imageListState.update { imageListItemDataDataList }
    }

    private fun updateImageMap(imageDataList: List<ImageData>) {
        imageDataMap.clear()
        imageDataMap.putAll(
            imageDataList.associateBy { imageData -> imageData.id }
        )
    }

    private fun handleFailedRequest(errorMessage: String) {
        _errorMessageState.update { errorMessage }
        Log.e(LOG_TAG, errorMessage)
    }

    private fun hideAllDialogs() {
        _isDetailsConfirmationDialogVisibleState.update { false }
        _selectedImageState.update { null }
    }

    private fun ImageData.toImageListItemData(): ImageListItemData {
        return converter.convert(this)
    }

    init {
        searchQueryValueState
            .debounce(500L)
            .mapLatest { searchQuery ->
                handleSearchRequest(searchQuery)
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }
}
