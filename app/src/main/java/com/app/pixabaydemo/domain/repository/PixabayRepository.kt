package com.app.pixabaydemo.domain.repository

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource

interface PixabayRepository {

    suspend fun findImages(
        q: String,
        imageType: String? = null,
        orientation: String? = null,
        category: String? = null,
        minWidth: Int? = null,
        minHeight: Int? = null,
        colors: String? = null,
        editorsChoice: Boolean? = null,
        safeSearch: Boolean? = null,
        order: String? = null,
        page: Int? = null,
        perPage: Int? = null
    ) : Resource<List<PixabayImageInfo>>
}
