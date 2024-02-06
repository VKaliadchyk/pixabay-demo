package com.app.pixabaydemo.presentation.ui.screen.gallery.model

data class ImageData(
    val id: Int,
    val previewUrl: String,
    val largeImageUrl: String,
    val webFormatUrl: String,
    val tags: List<String>,
    val username: String,
    val likesCount: Int,
    val downloadsCount: Int,
    val commentsCount: Int
) {
    companion object {
        val defaultValue = ImageData(
            id = 0,
            previewUrl = "",
            largeImageUrl = "",
            webFormatUrl = "",
            tags = emptyList(),
            username = "",
            likesCount = 0,
            downloadsCount = 0,
            commentsCount = 0
        )
    }
}
