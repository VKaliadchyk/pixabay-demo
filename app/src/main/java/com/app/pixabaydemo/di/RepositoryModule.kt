package com.app.pixabaydemo.di

import com.app.pixabaydemo.data.repository.ImageRepositoryImpl
import com.app.pixabaydemo.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideImageRepository(repository: ImageRepositoryImpl): ImageRepository
}
