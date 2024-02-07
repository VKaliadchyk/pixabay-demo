package com.app.pixabaydemo.presentation.ui.screen.gallery.model

data class GalleryScreenInitialValues(
    val searchBarValue: String,
    val imageList: List<ImageListItemData>,
    val selectedImage: ImageListItemData?,
    val errorMessage: String?,
    val isDetailsConfirmationDialogVisible: Boolean
)
