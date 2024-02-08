package com.app.pixabaydemo

import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.presentation.converter.ImageDataToDetailedImageDataConverter
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageDataToDetailedImageDataConverterTest {

    private val converter: Converter<ImageData, DetailedImageData> = ImageDataToDetailedImageDataConverter()

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

    private val DETAILED_IMAGE_DATA = DetailedImageData(
        id = IMAGE_DATA.id,
        imageUrl = IMAGE_DATA.largeImageURL,
        tags = IMAGE_DATA.tags.split(","),
        username = IMAGE_DATA.user,
        likesCount = IMAGE_DATA.likes,
        downloadsCount = IMAGE_DATA.downloads,
        commentsCount = IMAGE_DATA.comments
    )

    @Test
    fun `convert - should convert ImageData into DetailedImageData`() {
        //WHEN
        val result = converter.convert(IMAGE_DATA)

        //THEN
        assertEquals(DETAILED_IMAGE_DATA, result)
    }
}
