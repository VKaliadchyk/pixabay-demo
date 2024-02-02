package com.app.pixabaydemo.di

import com.app.pixabaydemo.data.repository.PixabayRepositoryImpl
import com.app.pixabaydemo.domain.repository.PixabayRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePixabayRepository(repository: PixabayRepositoryImpl): PixabayRepository
}
