package nl.vanderneut.mark

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import nl.vanderneut.mark.ui.MainViewModel
import nl.vanderneut.mark.ui.NewsApp576598
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