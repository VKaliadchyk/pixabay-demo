package com.app.pixabaydemo.domain.usecase.impl

import com.app.pixabaydemo.domain.entity.ImageData
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.entity.SearchParameters
import com.app.pixabaydemo.domain.repository.ImageRepository
import com.app.pixabaydemo.domain.usecase.GetImageUseCase

class GetImageUseCaseImpl(private val repository: ImageRepository) : GetImageUseCase {

    override suspend fun execute(parameters: SearchParameters): Resource<List<ImageData>> {
        return repository.findImages(parameters)
    }
}
