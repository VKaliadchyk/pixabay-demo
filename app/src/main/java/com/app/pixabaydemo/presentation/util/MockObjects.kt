package com.app.pixabaydemo.presentation.util

import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData

fun mockedImageData(username: String = "User"): ImageListItemData {
    return ImageListItemData.defaultValue.copy(
        username = username,
        tags = listOf(
            "Tag1", "Tag2", "Tag3",
            "Tag4", "Tag5", "Tag6"
        )
    )
}

fun mockedImageDataList(): List<ImageListItemData> {
    return mutableListOf<ImageListItemData>().apply {
        for (i in 0..10) {
            add(mockedImageData("User $i"))
        }
    }
}

fun mockedDetailedImageData(username: String = "User"): DetailedImageData {
    return DetailedImageData.defaultValue.copy(
        username = username,
        tags = listOf(
            "Tag1", "Tag2", "Tag3",
            "Tag4", "Tag5", "Tag6"
        )
    )
}
