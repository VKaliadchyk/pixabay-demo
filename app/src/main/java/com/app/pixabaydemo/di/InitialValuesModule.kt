package com.app.pixabaydemo.di

import com.app.pixabaydemo.presentation.ui.screen.gallery.model.GalleryScreenInitialValues
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InitialValuesModule {

    @Singleton
    @Provides
    fun provideGalleryScreenInitialValues(): GalleryScreenInitialValues {
        return GalleryScreenInitialValues(
            searchBarValue = "Fruits",
            imageList = emptyList(),
            selectedImage = null,
            isDetailsConfirmationDialogVisible = false
        )
    }
}
