package com.app.pixabaydemo.presentation.converter

import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDataToImageListItemDataConverter @Inject constructor() :
    Converter<ImageData, ImageListItemData> {

    override fun convert(source: ImageData): ImageListItemData {
        return ImageListItemData(
            id = source.id,
            previewImageUrl = source.largeImageURL,
            tags = source.tags.split(TAGS_DELIMITER),
            username = source.user
        )
    }

    companion object {
        private const val TAGS_DELIMITER = ","
    }
}
