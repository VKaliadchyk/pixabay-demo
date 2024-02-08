package com.app.pixabaydemo.domain.entity

data class SearchParameters(
    val query: String,
    val page: Int? = null,
    val perPage: Int? = null
)
