package com.app.pixabaydemo.presentation.util.extension

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.defaultHorizontalPadding(): Modifier = padding(horizontal = 8.dp)

fun Modifier.defaultPadding(): Modifier = padding(8.dp)

fun Modifier.horizontalPadding(padding: Dp): Modifier = padding(start = padding, end = padding)

fun Modifier.defaultSpacerHeight(): Modifier = height(8.dp)

fun Modifier.defaultSpacerWidth(): Modifier = width(8.dp)

fun Modifier.defaultScreenPadding() : Modifier = padding(16.dp)

fun Modifier.roundedCornerShape(
    topStart: Dp = 8.dp,
    topEnd: Dp = 8.dp,
    bottomStart: Dp = 8.dp,
    bottomEnd: Dp = 8.dp
): Modifier {
    return clip(
        RoundedCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomStart = bottomStart,
            bottomEnd = bottomEnd
        )
    )
}
