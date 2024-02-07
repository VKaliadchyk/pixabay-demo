package com.app.pixabaydemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.app.pixabaydemo.presentation.navigation.NavGraph
import com.app.pixabaydemo.presentation.navigation.NavigationManager
import com.app.pixabaydemo.presentation.ui.theme.PixabayDemoAppTheme
import com.app.pixabaydemo.presentation.util.extension.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PixabayDemoAppTheme {
                val navController = rememberNavController()
                navigationManager.destination.collectAsState().value.also { destination ->
                    navController.navigate(destination)
                }

                Surface {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
