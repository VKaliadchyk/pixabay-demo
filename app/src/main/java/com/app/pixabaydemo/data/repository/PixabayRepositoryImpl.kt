package com.app.pixabaydemo.data.repository

import android.util.Log
import com.app.pixabaydemo.data.remote.api.PixabayApi
import com.app.pixabaydemo.data.remote.credentials.PixabayApiKeyProvider
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.repository.PixabayRepository
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayRepositoryImpl @Inject constructor(
    private val pixabayApi: PixabayApi,
    private val pixabayApiKeyProvider: PixabayApiKeyProvider
) : PixabayRepository {

    private val LOG_TAG = this.javaClass.name

    override suspend fun findImages(
        q: String,
        imageType: String?,
        orientation: String?,
        category: String?,
        minWidth: Int?,
        minHeight: Int?,
        colors: String?,
        editorsChoice: Boolean?,
        safeSearch: Boolean?,
        order: String?,
        page: Int?,
        perPage: Int?
    ): Resource<List<PixabayImageInfo>> {
        try {
            val response = pixabayApi.searchImages(
                apiKey = pixabayApiKeyProvider.getApiKey(),
                q = q,
                imageType = imageType,
                orientation = orientation,
                category = category,
                minWidth = minWidth,
                minHeight = minHeight,
                colors = colors,
                editorsChoice = editorsChoice,
                safeSearch = safeSearch,
                order = order,
                page = page,
                perPage = perPage
            )

            if (response.isSuccessful) {
                val responseBody = response.body()
                    ?: return Resource.Failure("Response body is null")
                val hitList = responseBody.hits
                    ?: return Resource.Failure("Hit list is null")

                val pixabayImageInfoList = hitList
                    .map { hit -> hit.toPixabayImageInfo() }

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


    @Throws(NullPointerException::class)
    private fun Hit.toPixabayImageInfo(): PixabayImageInfo {
        return PixabayImageInfo(
            id = id!!,
            pageURL = pageURL!!,
            type = type!!,
            tags = tags!!,
            previewURL = previewURL!!,
            previewWidth = previewWidth!!,
            previewHeight = previewHeight!!,
            webFormatURL = webFormatURL!!,
            webFormatWidth = webFormatWidth!!,
            webFormatHeight = webFormatHeight!!,
            largeImageURL = largeImageURL!!,
            fullHDURL = fullHDURL,
            imageURL = imageURL,
            imageWidth = imageWidth!!,
            imageHeight = imageHeight!!,
            imageSize = imageSize!!,
            views = views!!,
            downloads = downloads!!,
            likes = likes!!,
            comments = comments!!,
            userId = userId!!,
            user = user!!,
            userImageURL = userImageURL!!
        )
    }
}
