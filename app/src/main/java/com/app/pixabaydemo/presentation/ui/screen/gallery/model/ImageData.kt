package com.app.pixabaydemo.presentation.ui.screen.gallery.model

data class ImageData(
    val id: Int,
    val previewImageUrl: String,
    val tags: List<String>,
    val username: String
) {
    companion object {
        val defaultValue = ImageData(
            id = 0,
            previewImageUrl = "",
            tags = emptyList(),
            username = "",
        )
    }
}
