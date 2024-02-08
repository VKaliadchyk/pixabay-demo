package com.app.pixabaydemo

import com.app.pixabaydemo.data.converter.HitToImageDataConverter
import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class HitToImageDataConverterTest {

    private val converter: Converter<Hit, ImageData> = HitToImageDataConverter()

    private val HIT = Hit(
        id = 1,
        pageURL = "pageURL",
        type = "type",
        tags = "tags",
        previewURL = "previewURL",
        previewWidth = 2,
        previewHeight = 3,
        webFormatURL = "webFormatURL",
        webFormatWidth = 4,
        webFormatHeight = 5,
        largeImageURL = "largeImageURL",
        fullHDURL = "fullHDURL",
        imageURL = "imageURL",
        imageWidth = 6,
        imageHeight = 7,
        imageSize = 8,
        views = 9,
        downloads = 10,
        likes = 11,
        comments = 12,
        userId = 13,
        user = "user",
        userImageURL = "userImageURL"
    )

    private val IMAGE_DATA = ImageData(
        id = 1,
        pageURL = "pageURL",
        type = "type",
        tags = "tags",
        previewURL = "previewURL",
        previewWidth = 2,
        previewHeight = 3,
        webFormatURL = "webFormatURL",
        webFormatWidth = 4,
        webFormatHeight = 5,
        largeImageURL = "largeImageURL",
        fullHDURL = "fullHDURL",
        imageURL = "imageURL",
        imageWidth = 6,
        imageHeight = 7,
        imageSize = 8,
        views = 9,
        downloads = 10,
        likes = 11,
        comments = 12,
        userId = 13,
        user = "user",
        userImageURL = "userImageURL"
    )

    @Test
    fun `convert - should convert Hit into ImageData`() {
        //WHEN
        val result = converter.convert(HIT)

        //THEN
        assertEquals(IMAGE_DATA, result)
    }

    @Test
    fun `convert - should throw NPE`() {
        //GIVEN
        val emptyHit = Hit()

        //WHEN, THEN
        assertThrows(NullPointerException::class.java) {
            converter.convert(emptyHit)
        }
    }
}
