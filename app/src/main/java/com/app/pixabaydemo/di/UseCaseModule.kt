package com.app.pixabaydemo.di

import com.app.pixabaydemo.domain.repository.ImageRepository
import com.app.pixabaydemo.domain.usecase.GetImageUseCase
import com.app.pixabaydemo.domain.usecase.impl.GetImageUseCaseImpl
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
    fun provideGetImageUseCase(imageRepository: ImageRepository): GetImageUseCase {
        return GetImageUseCaseImpl(imageRepository)
    }
}
