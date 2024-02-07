package com.app.pixabaydemo.presentation.ui.screen.imagedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.converter.Converter
import com.app.pixabaydemo.presentation.navigation.NavDestination
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    converter: Converter<PixabayImageInfo, DetailedImageData>,
    gson: Gson,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailedImageDataState = MutableStateFlow(DetailedImageData.defaultValue)
    val detailedImageDataState = _detailedImageDataState.asStateFlow()

    init {
        val pixabayImageInfoJson =
            savedStateHandle.get<String>(NavDestination.ImageDetailScreen.ARGUMENT_KEY)
        val pixabayImageInfo = gson.fromJson(pixabayImageInfoJson, PixabayImageInfo::class.java)
        val detailedImageData = converter.convert(pixabayImageInfo)

        _detailedImageDataState.update { detailedImageData }
    }
}
