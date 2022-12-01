package nl.vanderneut.mark

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.vanderneut.mark.ui.MainViewModel
import nl.vanderneut.mark.ui.NewsApp576598
import nl.vanderneut.mark.ui.screen.SplashScreen
import nl.vanderneut.mark.ui.theme.NewsApp576598Theme


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.newsResponse
        setContent {
            NewsApp576598Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Log.d("main says:", "opening576598 now")
                    NewsApp576598(viewModel)

                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    NewsApp576598Theme {
//        NewsApp576598(viewModel())
//    }
//}