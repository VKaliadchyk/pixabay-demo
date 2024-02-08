package com.app.pixabaydemo.data.converter

import com.app.pixabaydemo.data.remote.model.Hit
import com.app.pixabaydemo.domain.converter.Converter
import com.app.pixabaydemo.domain.entity.ImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HitToImageDataConverter @Inject constructor() : Converter<Hit, ImageData> {

    @Throws(NullPointerException::class)
    override fun convert(source: Hit): ImageData {
        return ImageData(
            id = source.id!!,
            pageURL = source.pageURL!!,
            type = source.type!!,
            tags = source.tags!!,
            previewURL = source.previewURL!!,
            previewWidth = source.previewWidth!!,
            previewHeight = source.previewHeight!!,
            webFormatURL = source.webFormatURL!!,
            webFormatWidth = source.webFormatWidth!!,
            webFormatHeight = source.webFormatHeight!!,
            largeImageURL = source.largeImageURL!!,
            fullHDURL = source.fullHDURL,
            imageURL = source.imageURL,
            imageWidth = source.imageWidth!!,
            imageHeight = source.imageHeight!!,
            imageSize = source.imageSize!!,
            views = source.views!!,
            downloads = source.downloads!!,
            likes = source.likes!!,
            comments = source.comments!!,
            userId = source.userId!!,
            user = source.user!!,
            userImageURL = source.userImageURL!!
        )
    }
}
