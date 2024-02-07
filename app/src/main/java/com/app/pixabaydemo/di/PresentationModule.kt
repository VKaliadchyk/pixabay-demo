package com.app.pixabaydemo.di

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.navigation.NavigationManager
import com.app.pixabaydemo.presentation.navigation.NavigationManagerImpl
import com.app.pixabaydemo.presentation.serialization.PixabayImageInfoSerializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Singleton
    @Provides
    fun provideNavigationManager(): NavigationManager {
        return NavigationManagerImpl()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(PixabayImageInfo::class.java, PixabayImageInfoSerializer())
            .create()
    }
}
