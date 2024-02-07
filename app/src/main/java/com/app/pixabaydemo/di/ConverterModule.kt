package com.app.pixabaydemo.di

import com.app.pixabaydemo.data.converter.HitToImageConverter
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.presentation.converter.ImageToDetailedImageDataConverter
import com.app.pixabaydemo.presentation.converter.ImageToImageListItemDataConverter
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConverterModule {

    @Singleton
    @Binds
    abstract fun provideImageToImageListItemDataConverter(
        converter: ImageToImageListItemDataConverter
    ): Converter<ImageData, ImageListItemData>

    @Singleton
    @Binds
    abstract fun provideImageToDetailedImageDataConverter(
        converter: ImageToDetailedImageDataConverter
    ): Converter<ImageData, DetailedImageData>

    @Singleton
    @Binds
    abstract fun provideHitToImageConverter(
        converter: HitToImageConverter
    ): Converter<Hit, ImageData>
}
