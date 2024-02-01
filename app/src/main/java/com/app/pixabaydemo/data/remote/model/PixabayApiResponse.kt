package com.app.pixabaydemo.data.remote.model

import com.google.gson.annotations.SerializedName

data class PixabayApiResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("totalHits") val totalHits: Int,
    @SerializedName("hits") val hits: List<Hit>?
)