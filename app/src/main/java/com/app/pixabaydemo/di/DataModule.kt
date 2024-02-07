package com.app.pixabaydemo.di

import com.app.pixabaydemo.data.cache.DataCache
import com.app.pixabaydemo.data.cache.LocalDataCache
import com.app.pixabaydemo.data.remote.credentials.PixabayApiKeyProvider
import com.app.pixabaydemo.data.remote.credentials.DefaultPixabayApiKeyProvider
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.SearchParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providePixabayApiKeyProvider(): PixabayApiKeyProvider {
        return DefaultPixabayApiKeyProvider()
    }


    @Singleton
    @Provides
    fun provideLocalDataCache(): DataCache<SearchParameters, List<ImageData>> {
        return LocalDataCache()
    }
}
