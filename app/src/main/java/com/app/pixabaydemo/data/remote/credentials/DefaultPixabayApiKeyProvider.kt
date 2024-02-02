package com.app.pixabaydemo.data.remote.credentials

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPixabayApiKeyProvider @Inject constructor() : PixabayApiKeyProvider {

    override fun getApiKey() = PIXABAY_API_KEY


    companion object {
        private const val PIXABAY_API_KEY = "33661850-d902be0a080d05a274f8e8ec7"
    }
}
