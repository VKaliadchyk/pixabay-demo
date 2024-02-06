package com.app.pixabaydemo.di

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.converter.Converter
import com.app.pixabaydemo.presentation.converter.PixabayImageInfoToImageDataConverter
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData
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
}
