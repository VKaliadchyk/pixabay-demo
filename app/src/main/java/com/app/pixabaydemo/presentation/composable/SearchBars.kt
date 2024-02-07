package com.app.pixabaydemo.presentation.composable


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.pixabaydemo.R
import com.app.pixabaydemo.presentation.ui.theme.PixabayDemoAppTheme
import com.app.pixabaydemo.presentation.util.extension.defaultPadding
import com.app.pixabaydemo.presentation.util.extension.defaultScreenPadding
import com.app.pixabaydemo.presentation.util.extension.defaultSpacerWidth

@Composable
fun GalleryScreenSearchBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {

    val hint: String = stringResource(id = R.string.gallery_search_bar_hint)
    val isHintDisplayed by remember(searchQuery) {
        mutableStateOf(searchQuery.isEmpty() && hint.isNotEmpty())
    }

    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .defaultScreenPadding()
    ) {
        Row(
            modifier = Modifier
                .defaultPadding()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "Searchbar icon",
                modifier = Modifier.padding(
                    start = 5.dp
                )
            )
            Spacer(modifier = Modifier.defaultSpacerWidth())
            Box(modifier = Modifier.fillMaxWidth()) {
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchQuery,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    onValueChange = { newValue -> onQueryChange(newValue) }
                )
                if (isHintDisplayed) {
                    Text(text = hint, color = Color.Gray)
                }
            }
        }
    }
}

@Preview
@Composable
fun ImageSearchBarPreview() {
    PixabayDemoAppTheme {
        Surface {
            GalleryScreenSearchBar(
                searchQuery = "Flowers",
                onQueryChange = { }
            )
        }
    }
}
