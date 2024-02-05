package com.app.pixabaydemo.presentation.ui.screen.gallery

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pixabaydemo.presentation.composable.AppCard
import com.app.pixabaydemo.presentation.composable.AppImage
import com.app.pixabaydemo.presentation.composable.GalleryScreenSearchBar
import com.app.pixabaydemo.presentation.composable.TagPill
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageData
import com.app.pixabaydemo.presentation.ui.theme.PixabayDemoAppTheme
import com.app.pixabaydemo.presentation.util.defaultHorizontalPadding
import com.app.pixabaydemo.presentation.util.defaultPadding
import com.app.pixabaydemo.presentation.util.defaultSpacerHeight
import com.app.pixabaydemo.presentation.util.mockedImageData
import com.app.pixabaydemo.presentation.util.mockedImageDataList
import com.app.pixabaydemo.presentation.util.roundedCornerShape
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GalleryScreen(
    imageList: List<ImageData>,
    searchQueryValue: String,
    onSearchQueryChange: (String) -> Unit,
    onListItemClick: (ImageData) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        GalleryScreenSearchBar(searchQuery = searchQueryValue, onQueryChange = onSearchQueryChange)
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items = imageList) { imageData ->
                ImageListItem(imageData = imageData, onItemClick = onListItemClick)
            }
        }
    }
}


@Composable
fun ImageListItem(
    imageData: ImageData,
    onItemClick: (ImageData) -> Unit
) {
    AppCard(
        modifier = Modifier
            .defaultPadding()
            .width(200.dp)
            .height(270.dp)
            .clickable {
                onItemClick(imageData)
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
                    imageUrl = imageData.largeImageUrl,
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
                    text = imageData.username,
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
                    imageData.tags.forEach { tag ->
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
                onSearchQueryChange = { },
                onListItemClick = { }
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
                imageData = mockedImageData(),
                onItemClick = { }
            )
        }
    }
}
