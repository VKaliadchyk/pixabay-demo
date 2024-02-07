package com.app.pixabaydemo.domain.repository

import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.entity.SearchParameters

interface ImageRepository {

    suspend fun findImages(parameters: SearchParameters) : Resource<List<ImageData>>
}
