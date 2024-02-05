package com.app.pixabaydemo.presentation.ui.screen.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class GalleryViewModel @Inject constructor() : ViewModel() {

    private val _imageListState = MutableStateFlow<List<ImageData>>(emptyList())
    val imageListState = _imageListState.asStateFlow()

    private val _searchQueryValueState = MutableStateFlow("")
    val searchQueryValueState = _searchQueryValueState.asStateFlow()


    fun onListItemClick(imageData: ImageData) {
        TODO("Not yet implemented")
    }

    fun onSearchQueryChange(query: String) {
        _searchQueryValueState.update { query }
    }

    private fun handleSearchQuery(searchQuery: String) {
        //TODO implement
    }

    init {

        searchQueryValueState
            .debounce(500L)
            .mapLatest { searchQuery ->
                handleSearchQuery(searchQuery)
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }
}
