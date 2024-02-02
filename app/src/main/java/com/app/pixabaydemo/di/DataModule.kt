package com.app.pixabaydemo.di

import com.app.pixabaydemo.data.remote.credentials.PixabayApiKeyProvider
import com.app.pixabaydemo.data.remote.credentials.DefaultPixabayApiKeyProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun providePixabayApiKeyProvider(apiKeyProvider: DefaultPixabayApiKeyProvider): PixabayApiKeyProvider
}
