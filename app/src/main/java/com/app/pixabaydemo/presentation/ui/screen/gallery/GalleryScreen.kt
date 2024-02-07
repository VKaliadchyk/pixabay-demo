package com.app.pixabaydemo.presentation.ui.screen.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pixabaydemo.R
import com.app.pixabaydemo.presentation.composable.AppCard
import com.app.pixabaydemo.presentation.composable.AppImage
import com.app.pixabaydemo.presentation.composable.GalleryScreenSearchBar
import com.app.pixabaydemo.presentation.composable.TagPill
import com.app.pixabaydemo.presentation.composable.ViewDetailsConfirmationDialog
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData
import com.app.pixabaydemo.presentation.ui.theme.PixabayDemoAppTheme
import com.app.pixabaydemo.presentation.util.extension.defaultHorizontalPadding
import com.app.pixabaydemo.presentation.util.extension.defaultPadding
import com.app.pixabaydemo.presentation.util.extension.defaultSpacerHeight
import com.app.pixabaydemo.presentation.util.mockedImageData
import com.app.pixabaydemo.presentation.util.mockedImageDataList
import com.app.pixabaydemo.presentation.util.extension.roundedCornerShape
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GalleryScreen(
    imageList: List<ImageListItemData>,
    searchQueryValue: String,
    isDetailsConfirmationDialogVisible: Boolean,
    selectedImage: ImageListItemData?,
    errorMessage: String?,
    onSearchQueryChange: (String) -> Unit,
    onListItemClick: (ImageListItemData) -> Unit,
    onDetailsConfirmationDialogConfirmClick: (ImageListItemData) -> Unit,
    onDialogDismissClick: () -> Unit
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (errorMessage != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_dialog_alert),
                    contentDescription = "Error"
                )
                Spacer(modifier = Modifier.defaultSpacerHeight())
                Text(
                    text = errorMessage,
                    color = Color.Gray
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            GalleryScreenSearchBar(
                searchQuery = searchQueryValue,
                onQueryChange = onSearchQueryChange
            )
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(items = imageList) { imageData ->
                    ImageListItem(imageListItemData = imageData, onItemClick = onListItemClick)
                }
            }
        }

        ViewDetailsConfirmationDialog(
            isVisible = isDetailsConfirmationDialogVisible && selectedImage != null,
            title = stringResource(id = R.string.dialog_image_details_title),
            message = stringResource(id = R.string.dialog_image_details_message),
            imageListItemData = selectedImage ?: ImageListItemData.defaultValue,
            onConfirmClick = onDetailsConfirmationDialogConfirmClick,
            onDismissClick = onDialogDismissClick
        )
    }
}


@Composable
fun ImageListItem(
    imageListItemData: ImageListItemData,
    onItemClick: (ImageListItemData) -> Unit
) {
    AppCard(
        modifier = Modifier
            .defaultPadding()
            .width(200.dp)
            .height(270.dp)
            .clickable {
                onItemClick(imageListItemData)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                AppImage(
                    imageUrl = imageListItemData.previewImageUrl,
                    contentDescription = null,
                    modifier = Modifier.roundedCornerShape(
                        topStart = 5.dp,
                        topEnd = 5.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
            }
            Column(
                modifier = Modifier.defaultHorizontalPadding()
            ) {
                Spacer(modifier = Modifier.defaultSpacerHeight())
                Text(
                    text = imageListItemData.username,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.defaultSpacerHeight())
                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    imageListItemData.tags.forEach { tag ->
                        TagPill(tag = tag)
                    }
                }
            }
        }
    }
}


// ============================= PREVIEWS =============================

@Preview
@Composable
fun GalleryScreenPreview() {
    PixabayDemoAppTheme {
        Surface {
            GalleryScreen(
                imageList = mockedImageDataList(),
                searchQueryValue = "Flowers",
                isDetailsConfirmationDialogVisible = false,
                selectedImage = null,
                errorMessage = null,
                onSearchQueryChange = { },
                onListItemClick = { },
                onDetailsConfirmationDialogConfirmClick = { },
                onDialogDismissClick = { }
            )
        }
    }
}

@Preview
@Composable
fun ImageListItemPreview() {
    PixabayDemoAppTheme {
        Surface {
            ImageListItem(
                imageListItemData = mockedImageData(),
                onItemClick = { }
            )
        }
    }
}
