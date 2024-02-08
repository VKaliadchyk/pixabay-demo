package com.app.pixabaydemo

import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.presentation.converter.ImageDataToImageListItemDataConverter
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageDataToImageListItemDataConverterTest {

    private val converter: Converter<ImageData, ImageListItemData> = ImageDataToImageListItemDataConverter()

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

    private val IMAGE_LIST_ITEM_DATA = ImageListItemData(
        id = IMAGE_DATA.id,
        previewImageUrl = IMAGE_DATA.largeImageURL,
        tags = IMAGE_DATA.tags.split(","),
        username = IMAGE_DATA.user
    )

    @Test
    fun `convert - should convert ImageData into ImageListItemData`() {
        //WHEN
        val result = converter.convert(IMAGE_DATA)

        //THEN
        assertEquals(IMAGE_LIST_ITEM_DATA, result)
    }
}
