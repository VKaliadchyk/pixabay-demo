package com.app.pixabaydemo.presentation.converter

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayImageInfoToImageDataConverter @Inject constructor() :
    Converter<PixabayImageInfo, ImageData> {

    override fun convert(source: PixabayImageInfo): ImageData {
        return ImageData(
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
