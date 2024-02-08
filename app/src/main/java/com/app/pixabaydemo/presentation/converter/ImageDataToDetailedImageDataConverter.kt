package com.app.pixabaydemo.presentation.converter

import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDataToDetailedImageDataConverter @Inject constructor() :
    Converter<ImageData, DetailedImageData> {

    override fun convert(source: ImageData): DetailedImageData {
        return DetailedImageData(
            id = source.id,
            imageUrl = source.largeImageURL,
            tags = source.tags.split(TAGS_DELIMITER),
            username = source.user,
            likesCount = source.likes,
            downloadsCount = source.downloads,
            commentsCount = source.comments
        )
    }

    companion object {
        private const val TAGS_DELIMITER = ","
    }
}
