package com.app.pixabaydemo.di

import com.app.pixabaydemo.data.converter.HitToImageDataConverter
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.presentation.converter.ImageDataToDetailedImageDataConverter
import com.app.pixabaydemo.presentation.converter.ImageDataToImageListItemDataConverter
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
    abstract fun provideImageDataToImageListItemDataConverter(
        converter: ImageDataToImageListItemDataConverter
    ): Converter<ImageData, ImageListItemData>

    @Singleton
    @Binds
    abstract fun provideImageDataToDetailedImageDataConverter(
        converter: ImageDataToDetailedImageDataConverter
    ): Converter<ImageData, DetailedImageData>

    @Singleton
    @Binds
    abstract fun provideHitToImageDataConverter(
        converter: HitToImageDataConverter
    ): Converter<Hit, ImageData>
}
