package com.app.pixabaydemo.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

@Composable
fun FadeAnimatedVisibility(
    isVisible: Boolean,
    enterDuration: Int = 700,
    exitDuration: Int = 500,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(enterDuration)),
        exit = fadeOut(tween(exitDuration)),
        content = content
    )
}