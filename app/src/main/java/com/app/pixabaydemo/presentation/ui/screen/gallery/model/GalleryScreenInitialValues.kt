package com.app.pixabaydemo.presentation.ui.screen.gallery.model

data class GalleryScreenInitialValues(
    val searchBarValue: String,
    val imageList: List<ImageData>,
    val selectedImage: ImageData?,
    val errorMessage: String?,
    val isDetailsConfirmationDialogVisible: Boolean
)
