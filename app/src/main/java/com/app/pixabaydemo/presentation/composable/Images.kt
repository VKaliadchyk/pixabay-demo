package com.app.pixabaydemo.presentation.composable

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.Transformation

@Composable
fun AppImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    transformation: Transformation? = null,
) {

    val imageRequestBuilder = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .scale(Scale.FILL)
        .error(android.R.drawable.ic_dialog_alert)
        .crossfade(true)
        .crossfade(500)

    val imageRequest = if (transformation != null) {
        imageRequestBuilder.transformations(transformation).build()
    } else {
        imageRequestBuilder.build()
    }

    SubcomposeAsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        loading = {
            CircularProgressIndicator()
        },
        contentScale = contentScale
    )
}
