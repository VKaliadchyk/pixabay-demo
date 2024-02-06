package com.app.pixabaydemo.presentation.ui.screen.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.usecase.GetPixabayImagesUseCase
import com.app.pixabaydemo.presentation.converter.Converter
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.GalleryScreenInitialValues
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData
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
    private val converter: Converter<PixabayImageInfo, ImageData>,
    initialValues: GalleryScreenInitialValues
) : ViewModel() {

    private val LOG_TAG = this.javaClass.name

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
        _isDetailsConfirmationDialogVisibleState.update { false }
        _selectedImage.update { null }
    }

    fun onConfirmDetailsConfirmationDialog(imageData: ImageData) {
        TODO("Not yet implemented")
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
        val imageDataList = pixabayImageInfoList
            .map { pixabayImageInfo -> pixabayImageInfo.toImageData() }

        _imageListState.update { imageDataList }
    }

    private fun handleFailedRequest(errorMessage: String) {
        Log.e(LOG_TAG, errorMessage)
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
