
package com.example.ArtesanApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ArtesanApp.navigation.AppNavigation
import com.example.ArtesanApp.ui.theme.ArtesanAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArtesanAppTheme {
                AppNavigation()
            }
        }
    }
}