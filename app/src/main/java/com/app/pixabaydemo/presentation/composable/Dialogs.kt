package com.app.pixabaydemo.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.pixabaydemo.R
import com.app.pixabaydemo.presentation.ui.screen.gallery.model.ImageListItemData


@Composable
fun ViewDetailsConfirmationDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    title: String,
    message: String,
    imageListItemData: ImageListItemData,
    onConfirmClick: (ImageListItemData) -> Unit,
    onDismissClick: () -> Unit,
) {
    BaseDialog(
        modifier = modifier,
        title = title,
        text = message,
        confirmButton = { StandardButton(text = stringResource(id = R.string.title_ok), onClick = { onConfirmClick(imageListItemData) }) },
        dismissButton = { StandardButton(text = stringResource(id = R.string.title_cancel), onClick = onDismissClick) },
        isVisible = isVisible
    )
}

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: String? = null,
    text: String? = null,
    isVisible: Boolean
) {
    FadeAnimatedVisibility(isVisible = isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.50f))
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = {
                            /* do nothing to prevent clicking on a background
                            * used detectTapGestures() to get rid of ripple animation */
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                shadowElevation = 8.dp,
                tonalElevation = 3.dp,
                shape = AlertDialogDefaults.shape,
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    icon?.let { icon ->
                        icon()
                    }
                    title?.let { title ->
                        Text(text = title, style = MaterialTheme.typography.headlineSmall)
                    }
                    text?.let { text ->
                        Text(text = text)
                    }
                    content?.let { content ->
                        content()
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            dismissButton?.let {
                                dismissButton()
                            }
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            confirmButton()
                        }
                    }
                }
            }
        }
    }
}
