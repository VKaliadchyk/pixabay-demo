package com.app.pixabaydemo.data.repository

import android.util.Log
import com.app.pixabaydemo.data.cache.DataCache
import com.app.pixabaydemo.data.remote.api.PixabayApi
import com.app.pixabaydemo.data.remote.credentials.PixabayApiKeyProvider
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.entity.SearchParameters
import com.app.pixabaydemo.domain.repository.ImageRepository
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val pixabayApi: PixabayApi,
    private val pixabayApiKeyProvider: PixabayApiKeyProvider,
    private val converter: Converter<Hit, ImageData>,
    private val dataCache: DataCache<SearchParameters, List<ImageData>>
) : ImageRepository {

    private val LOG_TAG = this.javaClass.name

    override suspend fun findImages(parameters: SearchParameters): Resource<List<ImageData>> {
        val cachedValue = dataCache.get(parameters)

        if (cachedValue != null) {
            return Resource.Success(cachedValue)
        } else {
            try {
                val response = pixabayApi.searchImages(
                    apiKey = pixabayApiKeyProvider.getApiKey(),
                    q = parameters.query,
                    imageType = null,
                    orientation = null,
                    category = null,
                    minWidth = null,
                    minHeight = null,
                    colors = null,
                    editorsChoice = null,
                    safeSearch = null,
                    order = null,
                    page = parameters.page,
                    perPage = parameters.perPage
                )

                if (response.isSuccessful) {
                    val responseBody = response.body()
                        ?: return Resource.Failure("Response body is null")
                    val hitList = responseBody.hits
                        ?: return Resource.Failure("Hit list is null")

                    val pixabayImageInfoList = hitList
                        .map { hit -> hit.toPixabayImageInfo() }

                    dataCache.put(parameters, pixabayImageInfoList)
                    return Resource.Success(pixabayImageInfoList)
                } else {
                    return Resource.Failure(
                        message = "Failed request. Code: ${response.code()}. Message: ${response.message()}"
                    )
                }

            } catch (te: TimeoutException) {
                Log.e(LOG_TAG, te.message, te)

                return Resource.Failure(
                    message = "Request timeout"
                )
            } catch (npe: NullPointerException) {
                Log.e(LOG_TAG, npe.message, npe)

                return Resource.Failure(
                    message = "Incomplete data. One or some of fields are null"
                )
            } catch (e: Exception) {
                Log.e(LOG_TAG, e.message, e)

                return Resource.Failure(
                    message = "Failure. Message: ${e.message}"
                )
            }
        }
    }


    @Throws(NullPointerException::class)
    private fun Hit.toPixabayImageInfo(): ImageData {
        return converter.convert(this)
    }
}
