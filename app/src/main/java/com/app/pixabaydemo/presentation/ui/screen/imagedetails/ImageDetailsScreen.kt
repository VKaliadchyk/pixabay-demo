package com.app.pixabaydemo.presentation.ui.screen.imagedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pixabaydemo.R
import com.app.pixabaydemo.presentation.composable.AppImage
import com.app.pixabaydemo.presentation.composable.IconText
import com.app.pixabaydemo.presentation.composable.LabeledText
import com.app.pixabaydemo.presentation.composable.TagPill
import com.app.pixabaydemo.presentation.ui.screen.imagedetails.model.DetailedImageData
import com.app.pixabaydemo.presentation.ui.theme.PixabayDemoAppTheme
import com.app.pixabaydemo.presentation.util.extension.defaultPadding
import com.app.pixabaydemo.presentation.util.extension.defaultSpacerHeight
import com.app.pixabaydemo.presentation.util.extension.defaultSpacerWidth
import com.app.pixabaydemo.presentation.util.mockedDetailedImageData
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ImageDetailsScreen(
    detailedImageData: DetailedImageData
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                AppImage(
                    imageUrl = detailedImageData.imageUrl,
                    contentDescription = "Large image",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.defaultSpacerHeight())
        Row(
            modifier = Modifier
                .defaultPadding()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .defaultPadding()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        LabeledText(
                            modifier = Modifier.fillMaxWidth(),
                            label = stringResource(R.string.title_uploaded_by),
                            text = detailedImageData.username,
                            labelStyle = MaterialTheme.typography.bodyLarge,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            labelFontWeight = FontWeight.Normal,
                            textFontWeight = FontWeight.Bold,
                            labelColor = MaterialTheme.colorScheme.primary,
                            textColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.defaultSpacerHeight())
                        FlowRow(
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 10.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            detailedImageData.tags.forEach { tag ->
                                TagPill(tag = tag)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.defaultSpacerHeight())
                Divider()
                Spacer(modifier = Modifier.defaultSpacerHeight())
                ImageStats(
                    detailedImageData.likesCount,
                    detailedImageData.commentsCount,
                    detailedImageData.downloadsCount
                )
            }
        }
    }
}

@Composable
fun ImageStats(
    likesCount: Int,
    commentsCount: Int,
    downloadsCount: Int,
) {
    Row(
        modifier = Modifier
            .defaultPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconText(
            text = likesCount.toString(),
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_like,
            contentDescription = "Likes"
        )
        Spacer(modifier = Modifier.defaultSpacerWidth())
        IconText(
            text = commentsCount.toString(),
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_comment,
            contentDescription = "Comments"
        )
        Spacer(modifier = Modifier.defaultSpacerWidth())
        IconText(
            text = downloadsCount.toString(),
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_download,
            contentDescription = "Downloads"
        )
    }
}


// ============================= PREVIEWS =============================

@Preview
@Composable
fun ImageDetailsScreenPreview() {
    PixabayDemoAppTheme {
        Surface {
            ImageDetailsScreen(
                detailedImageData = mockedDetailedImageData()
            )
        }
    }
}

@Preview
@Composable
fun ImageStatsPreview() {
    PixabayDemoAppTheme {
        Surface {
            ImageStats(
                likesCount = 5,
                commentsCount = 10,
                downloadsCount = 25
            )
        }
    }
}
