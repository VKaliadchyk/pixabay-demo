package com.app.pixabaydemo.domain.usecase

import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.entity.SearchParameters

interface GetImageUseCase {

    suspend fun execute(parameters: SearchParameters): Resource<List<ImageData>>
}
