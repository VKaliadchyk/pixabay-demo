package com.app.pixabaydemo.presentation.ui.screen.imagedetails

import androidx.lifecycle.ViewModel
import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.converter.Converter
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val converter: Converter<PixabayImageInfo, DetailedImageData>
) : ViewModel() {

}