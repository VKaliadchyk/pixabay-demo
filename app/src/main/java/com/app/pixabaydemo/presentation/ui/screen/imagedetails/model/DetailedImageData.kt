package com.app.pixabaydemo.presentation.ui.screen.imagedetails.model

data class DetailedImageData(
    val id: Int,
    val imageUrl: String,
    val tags: List<String>,
    val username: String,
    val likesCount: Int,
    val downloadsCount: Int,
    val commentsCount: Int
) {
    companion object {
        val defaultValue = DetailedImageData(
            id = 0,
            imageUrl = "",
            tags = emptyList(),
            username = "",
            likesCount = 0,
            downloadsCount = 0,
            commentsCount = 0
        )
    }
}
