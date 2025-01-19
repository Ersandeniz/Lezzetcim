package com.example.yemeim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.yemeim.ui.theme.YemeğimTheme
import com.example.yemeim.uix.viewModel.AnasayfaViewModel
import com.example.yemeim.uix.viewModel.DetayViewModel
import com.example.yemeim.uix.viewModel.SepetimViewModel
import com.example.yemeim.uix.views.SayfaGecisleri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val anasayfaViewModel: AnasayfaViewModel by viewModels()
    val DetayViewModel: DetayViewModel by viewModels()
    val sepetimViewModel :SepetimViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemeğimTheme {

                SayfaGecisleri(anasayfaViewModel,DetayViewModel,sepetimViewModel)

            }
        }

    }
}

