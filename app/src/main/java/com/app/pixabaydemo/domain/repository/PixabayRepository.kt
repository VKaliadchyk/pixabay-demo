package com.app.pixabaydemo.domain.repository

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.app.pixabaydemo.domain.entity.Resource
import retrofit2.http.Query

interface PixabayRepository {

    suspend fun findImages(
        @Query("key") apiKey: String,
        @Query("q") q: String,
        @Query("image_type") imageType: String? = null,
        @Query("orientation") orientation: String? = null,
        @Query("category") category: String? = null,
        @Query("min_width") minWidth: Int? = null,
        @Query("min_height") minHeight: Int? = null,
        @Query("colors") colors: String? = null,
        @Query("editors_choice") editorsChoice: Boolean? = null,
        @Query("safe_search") safeSearch: Boolean? = null,
        @Query("order") order: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null
    ) : Resource<List<PixabayImageInfo>>
}
