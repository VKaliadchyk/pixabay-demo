package com.app.pixabaydemo.presentation.util

import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData

fun mockedImageData(username: String = "User"): ImageData {
    return ImageData.defaultValue.copy(
        username = username,
        tags = listOf(
            "Tag1", "Tag2", "Tag3",
            "Tag4", "Tag5", "Tag6"
        )
    )
}

fun mockedImageDataList(): List<ImageData> {
    return mutableListOf<ImageData>().apply {
        for (i in 0..10) {
            add(mockedImageData("User $i"))
        }
    }
}