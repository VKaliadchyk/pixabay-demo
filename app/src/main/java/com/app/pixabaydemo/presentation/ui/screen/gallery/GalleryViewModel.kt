package com.app.pixabaydemo.presentation.ui.screen.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.usecase.GetPixabayImagesUseCase
import com.app.pixabaydemo.presentation.converter.Converter
import com.app.pixabaydemo.presentation.navigation.NavDestination
import com.app.pixabaydemo.presentation.navigation.NavigationManager
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.GalleryScreenInitialValues
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData
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
    private val getPixabayImagesUseCase: GetPixabayImagesUseCase,
    private val navigationManager: NavigationManager,
    private val converter: Converter<PixabayImageInfo, ImageData>,
    private val gson: Gson,
    initialValues: GalleryScreenInitialValues
) : ViewModel() {

    private val LOG_TAG = this.javaClass.name

    private val pixabayImageInfoMap = mutableMapOf<Int, PixabayImageInfo>()

    private val _selectedImage: MutableStateFlow<ImageData?> = MutableStateFlow(initialValues.selectedImage)
    val selectedImage = _selectedImage.asStateFlow()

    private val _isDetailsConfirmationDialogVisibleState = MutableStateFlow(initialValues.isDetailsConfirmationDialogVisible)
    val isDetailsConfirmationDialogVisibleState = _isDetailsConfirmationDialogVisibleState.asStateFlow()

    private val _imageListState = MutableStateFlow(initialValues.imageList)
    val imageListState = _imageListState.asStateFlow()

    private val _searchQueryValueState = MutableStateFlow(initialValues.searchBarValue)
    val searchQueryValueState = _searchQueryValueState.asStateFlow()


    fun onListItemClick(imageData: ImageData) {
        _isDetailsConfirmationDialogVisibleState.update { true }
        _selectedImage.update { imageData }
    }

    fun onSearchQueryChange(query: String) {
        _searchQueryValueState.update { query }
    }

    fun dismissDialogs() {
        hideAllDialogs()
    }

    fun onConfirmDetailsConfirmationDialog(imageData: ImageData) {
        hideAllDialogs()

        try {
            val pixabayImageInfo = pixabayImageInfoMap[imageData.id]!!
            val json = gson.toJson(pixabayImageInfo)
            navigationManager.navigate(NavDestination.ImageDetailScreen(json))
        } catch (npe: NullPointerException) {
            Log.e(LOG_TAG, npe.message, npe)
            //TODO show message to the user as well
        }
    }

    private suspend fun handleSearchRequest(searchQuery: String) {
        val resource = getPixabayImagesUseCase.execute(searchQuery)

        when (resource) {
            is Resource.Success -> {
                handleSuccessfulRequest(resource.data)
            }

            is Resource.Failure -> {
                handleFailedRequest(resource.message)
            }
        }
    }

    private fun handleSuccessfulRequest(pixabayImageInfoList: List<PixabayImageInfo>) {
        updatePixabayImageInfoMap(pixabayImageInfoList)

        val imageDataList = pixabayImageInfoList
            .map { pixabayImageInfo -> pixabayImageInfo.toImageData() }

        _imageListState.update { imageDataList }
    }

    private fun updatePixabayImageInfoMap(pixabayImageInfoList: List<PixabayImageInfo>) {
        pixabayImageInfoMap.clear()
        pixabayImageInfoMap.putAll(
            pixabayImageInfoList.associateBy { pixabayImageInfo -> pixabayImageInfo.id }
        )
    }

    private fun handleFailedRequest(errorMessage: String) {
        Log.e(LOG_TAG, errorMessage)
    }

    private fun hideAllDialogs() {
        _isDetailsConfirmationDialogVisibleState.update { false }
        _selectedImage.update { null }
    }

    private fun PixabayImageInfo.toImageData(): ImageData {
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
