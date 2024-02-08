package com.app.pixabaydemo

import android.util.Log
import com.app.pixabaydemo.data.cache.DataCache
import com.app.pixabaydemo.data.remote.api.PixabayApi
import com.app.pixabaydemo.data.remote.credentials.PixabayApiKeyProvider
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.data.remote.model.PixabayApiResponse
import com.app.pixabaydemo.data.repository.ImageRepositoryImpl
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.entity.SearchParameters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.lang.NullPointerException
import java.util.concurrent.TimeoutException

class ImageDataRepositoryImplTest {

    companion object {
        private const val FAKE_QUERY = "FAKE_QUERY"
        private const val FAKE_API_KEY = "FAKE_API_KEY"
        private const val FAKE_RESPONSE_BODY = "FAKE_RESPONSE_BODY"
    }

    private val FAKE_HIT = Hit(
        id = 0,
        pageURL = "",
        type = "",
        tags = "",
        previewURL = "",
        previewWidth = 0,
        previewHeight = 0,
        webFormatURL = "",
        webFormatWidth = 0,
        webFormatHeight = 0,
        largeImageURL = "",
        fullHDURL = "",
        imageURL = "",
        imageWidth = 0,
        imageHeight = 0,
        imageSize = 0,
        views = 0,
        downloads = 0,
        likes = 0,
        comments = 0,
        userId = 0,
        user = "",
        userImageURL = ""
    )

    private val FAKE_IMAGE_DATA = ImageData(
        id = 0,
        pageURL = "",
        type = "",
        tags = "",
        previewURL = "",
        previewWidth = 0,
        previewHeight = 0,
        webFormatURL = "",
        webFormatWidth = 0,
        webFormatHeight = 0,
        largeImageURL = "",
        fullHDURL = "",
        imageURL = "",
        imageWidth = 0,
        imageHeight = 0,
        imageSize = 0,
        views = 0,
        downloads = 0,
        likes = 0,
        comments = 0,
        userId = 0,
        user = "",
        userImageURL = ""
    )

    private val FAKE_SEARCH_PARAMETERS = SearchParameters(query = FAKE_QUERY)

    @MockK
    lateinit var pixabayApi: PixabayApi

    @MockK
    lateinit var pixabayApiKeyProvider: PixabayApiKeyProvider

    @MockK
    lateinit var dataCache: DataCache<SearchParameters, List<ImageData>>

    @MockK
    lateinit var converter: Converter<Hit, ImageData>

    private lateinit var repository: ImageRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        repository = ImageRepositoryImpl(
            pixabayApi,
            pixabayApiKeyProvider,
            converter,
            dataCache
        )

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        every { pixabayApiKeyProvider.getApiKey() } returns FAKE_API_KEY
        every { dataCache.get(FAKE_SEARCH_PARAMETERS) } returns null
        every { converter.convert(FAKE_HIT) } returns FAKE_IMAGE_DATA
    }

    @Test
    fun `searchImages - should return Failure with appropriate message request is not successful`() =
        runBlocking {
            // GIVEN
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.error(400, FAKE_RESPONSE_BODY.toResponseBody())

            // WHEN
            val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
            assertTrue(result is Resource.Failure)
            assertEquals(
                "Failed request. Code: 400. Message: Response.error()",
                (result as? Resource.Failure)?.message
            )
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when response body is null`() =
        runBlocking {
            // GIVEN
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.success(null)

            // WHEN
            val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
            assertTrue(result is Resource.Failure)
            assertEquals("Response body is null", (result as? Resource.Failure)?.message)
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when Hit list is null`() =
        runBlocking {
            // GIVEN
            val fakePixabayApiResponse = PixabayApiResponse(0, 0, null)
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.success(fakePixabayApiResponse)

            // WHEN
            val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
            assertTrue(result is Resource.Failure)
            assertEquals("Hit list is null", (result as? Resource.Failure)?.message)
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when failing to convert to entity`() =
        runBlocking {
            // GIVEN
            val emptyHitModel = Hit()
            val fakePixabayApiResponse = PixabayApiResponse(0, 0, listOf(emptyHitModel))
            every { converter.convert(emptyHitModel) } throws NullPointerException("Fake Exception")
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.success(fakePixabayApiResponse)

            // WHEN
            val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
            verify { converter.convert(emptyHitModel) }
            assertTrue(result is Resource.Failure)
            assertEquals(
                "Incomplete data. One or some of fields are null",
                (result as? Resource.Failure)?.message
            )
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when TimeoutException thrown`() =
        runBlocking {
            // GIVEN
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } throws TimeoutException()

            // WHEN
            val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
            assertTrue(result is Resource.Failure)
            assertEquals("Request timeout", (result as? Resource.Failure)?.message)
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when Exception thrown`() =
        runBlocking {
            // GIVEN
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } throws Exception("Fake Exception")

            // WHEN
            val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
            assertTrue(result is Resource.Failure)
            assertEquals("Failure. Message: Fake Exception", (result as? Resource.Failure)?.message)
        }

    @Test
    fun `searchImages - should return Success with not empty body`() = runBlocking {
        // GIVEN
        val fakePixabayApiResponse = PixabayApiResponse(0, 0, listOf(FAKE_HIT))
        coEvery {
            pixabayApi.searchImages(apiKey = any(), q = any())
        } returns Response.success(200, fakePixabayApiResponse)

        // WHEN
        val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

        // THEN
        coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
        verify { pixabayApiKeyProvider.getApiKey() }
        verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
        verify { converter.convert(FAKE_HIT) }
        assertTrue(result is Resource.Success)
        assertTrue((result as? Resource.Success)?.data?.isNotEmpty() ?: false)
    }

    @Test
    fun `searchImages - should not call PixabayApi if DataCache has a cached value`() = runBlocking {
        // GIVEN
        val fakePixabayApiResponse = PixabayApiResponse(0, 0, listOf(FAKE_HIT))
        every { dataCache.get(FAKE_SEARCH_PARAMETERS) } returns listOf(FAKE_IMAGE_DATA)
        coEvery {
            pixabayApi.searchImages(apiKey = any(), q = any())
        } returns Response.success(200, fakePixabayApiResponse)

        // WHEN
        val result = repository.findImages(FAKE_SEARCH_PARAMETERS)

        // THEN
        verify { dataCache.get(FAKE_SEARCH_PARAMETERS) }
        coVerify(exactly = 0) { pixabayApi.searchImages(apiKey = any(), q = any()) }
        verify(exactly = 0) { pixabayApiKeyProvider.getApiKey() }
        assertTrue(result is Resource.Success)
        assertTrue((result as? Resource.Success)?.data?.isNotEmpty() ?: false)
    }
}
