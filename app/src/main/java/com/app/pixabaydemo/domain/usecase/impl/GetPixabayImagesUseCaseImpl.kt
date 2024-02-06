package com.app.pixabaydemo.domain.usecase.impl

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource
import com.app.pixabaydemo.domain.repository.PixabayRepository
import com.app.pixabaydemo.domain.usecase.GetPixabayImagesUseCase

class GetPixabayImagesUseCaseImpl(
    private val repository: PixabayRepository
) : GetPixabayImagesUseCase {

    override suspend fun execute(query: String): Resource<List<PixabayImageInfo>> {
        return repository.findImages(query)
    }
}
