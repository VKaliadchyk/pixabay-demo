package com.app.pixabaydemo.presentation.converter

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayImageInfoToDetailedImageDataConverter @Inject constructor() :
    Converter<PixabayImageInfo, DetailedImageData> {

    override fun convert(source: PixabayImageInfo): DetailedImageData {
        return DetailedImageData(
            id = source.id,
            previewUrl = source.previewURL,
            largeImageUrl = source.largeImageURL,
            webFormatUrl = source.largeImageURL,
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
