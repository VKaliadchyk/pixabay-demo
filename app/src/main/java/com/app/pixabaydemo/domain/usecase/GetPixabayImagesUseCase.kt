package com.app.pixabaydemo.domain.usecase

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource

interface GetPixabayImagesUseCase {

    suspend fun execute(query: String): Resource<List<PixabayImageInfo>>
}
