package com.app.pixabaydemo.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.pixabaydemo.presentation.util.extension.defaultSpacerWidth

@Composable
fun TagPill(
    tag: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(6.dp)
    ) {
        Text(
            text = "#$tag",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun IconText(
    text: String,
    modifier: Modifier,
    icon: Int,
    contentDescription: String,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        tint = iconTint
    )
    Spacer(modifier = Modifier.defaultSpacerWidth())
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun LabeledText(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    labelStyle: TextStyle = LocalTextStyle.current,
    textStyle: TextStyle = LocalTextStyle.current,
    labelFontWeight: FontWeight? = null,
    textFontWeight: FontWeight? = null,
    labelColor: Color = Color.Unspecified,
    textColor: Color = Color.Unspecified,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            style = labelStyle,
            fontWeight = labelFontWeight,
            color = labelColor
        )
        Text(
            text = text,
            style = textStyle,
            fontWeight = textFontWeight,
            color = textColor
        )
    }
}
