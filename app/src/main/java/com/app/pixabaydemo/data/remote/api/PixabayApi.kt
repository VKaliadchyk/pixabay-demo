package com.app.pixabaydemo.data.remote.api

import com.app.pixabaydemo.data.remote.model.PixabayApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun searchImages(
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
    ): Response<PixabayApiResponse>
}
