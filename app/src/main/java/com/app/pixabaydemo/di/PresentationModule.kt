package com.app.pixabaydemo.di

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.converter.Converter
import com.app.pixabaydemo.presentation.converter.PixabayImageInfoToDetailedImageDataConverter
import com.app.pixabaydemo.presentation.converter.PixabayImageInfoToImageDataConverter
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PresentationModule {

    @Binds
    abstract fun providePixabayImageInfoToImageDataConverter(
        converter: PixabayImageInfoToImageDataConverter
    ): Converter<PixabayImageInfo, ImageData>

    @Binds
    abstract fun providePixabayImageInfoToDetailedImageDataConverter(
        converter: PixabayImageInfoToDetailedImageDataConverter
    ): Converter<PixabayImageInfo, DetailedImageData>
}
