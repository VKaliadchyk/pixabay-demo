package com.app.pixabaydemo

import android.util.Log
import com.app.pixabaydemo.data.remote.api.PixabayApi
import com.app.pixabaydemo.data.remote.credentials.PixabayApiKeyProvider
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.data.remote.model.PixabayApiResponse
import com.app.pixabaydemo.data.repository.PixabayRepositoryImpl
import com.app.pixabaydemo.domain.entity.Resource
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
import java.util.concurrent.TimeoutException

class PixabayRepositoryImplTest {

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

    @MockK
    lateinit var pixabayApi: PixabayApi

    @MockK
    lateinit var pixabayApiKeyProvider: PixabayApiKeyProvider

    private lateinit var repository: PixabayRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        repository = PixabayRepositoryImpl(
            pixabayApi,
            pixabayApiKeyProvider
        )

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0

        every { pixabayApiKeyProvider.getApiKey() } returns FAKE_API_KEY
    }

    @Test
    fun `searchImages - should return Failure with appropriate message request is not successful`() =
        runBlocking {
            // GIVEN
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.error(400, FAKE_RESPONSE_BODY.toResponseBody())

            // WHEN
            val result = repository.findImages(q = FAKE_QUERY)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
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
            val result = repository.findImages(q = FAKE_QUERY)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            assertTrue(result is Resource.Failure)
            assertEquals("Response body is null", (result as? Resource.Failure)?.message)
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when hit list is null`() =
        runBlocking {
            // GIVEN
            val fakePixabayApiResponse = PixabayApiResponse(0, 0, null)
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.success(fakePixabayApiResponse)

            // WHEN
            val result = repository.findImages(q = FAKE_QUERY)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
            assertTrue(result is Resource.Failure)
            assertEquals("Hit list is null", (result as? Resource.Failure)?.message)
        }

    @Test
    fun `searchImages - should return Failure with appropriate message when failing to convert to entity`() =
        runBlocking {
            // GIVEN
            val emptyHitModel = Hit()
            val fakePixabayApiResponse = PixabayApiResponse(0, 0, listOf(emptyHitModel))
            coEvery {
                pixabayApi.searchImages(apiKey = any(), q = any())
            } returns Response.success(fakePixabayApiResponse)

            // WHEN
            val result = repository.findImages(q = FAKE_QUERY)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
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
            val result = repository.findImages(q = FAKE_QUERY)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
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
            val result = repository.findImages(q = FAKE_QUERY)

            // THEN
            coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
            verify { pixabayApiKeyProvider.getApiKey() }
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
        val result = repository.findImages(q = FAKE_QUERY)

        // THEN
        coVerify { pixabayApi.searchImages(apiKey = FAKE_API_KEY, q = FAKE_QUERY) }
        verify { pixabayApiKeyProvider.getApiKey() }
        assertTrue(result is Resource.Success)
        assertTrue((result as? Resource.Success)?.data?.isNotEmpty() ?: false)
    }
}
