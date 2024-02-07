package com.app.pixabaydemo.presentation.ui.screen.gallery.model

data class ImageListItemData(
    val id: Int,
    val previewImageUrl: String,
    val tags: List<String>,
    val username: String
) {
    companion object {
        val defaultValue = ImageListItemData(
            id = 0,
            previewImageUrl = "",
            tags = emptyList(),
            username = "",
        )
    }
}
