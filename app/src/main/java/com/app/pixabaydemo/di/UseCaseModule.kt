package com.app.pixabaydemo.di

import com.app.pixabaydemo.domain.repository.PixabayRepository
import com.app.pixabaydemo.domain.usecase.GetPixabayImagesUseCase
import com.app.pixabaydemo.domain.usecase.impl.GetPixabayImagesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPixabayImagesUseCase(pixabayRepository: PixabayRepository): GetPixabayImagesUseCase {
        return GetPixabayImagesUseCaseImpl(pixabayRepository)
    }
}
