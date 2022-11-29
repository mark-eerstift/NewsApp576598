package nl.vanderneut.mark.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.vanderneut.mark.NewsData

@Composable
fun DetailScreen(navController: NavController, newsData: NewsData){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Details", fontWeight = FontWeight.SemiBold)
        Button(onClick = {
            //navController.navigate("TopNews")
            //Closes the current screen
            navController.popBackStack()
        }) {
            Text(text = "go to News + ${newsData.author}")
        }
    }
}

@Preview()
@Composable
fun DetailsScreenPreview()
{
    DetailScreen(rememberNavController(), NewsData(
        1,
        author = "Raja Razek, CNN",
        title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
        description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
        publishedAt = "2021-11-04T05:35:21Z"
    ))
}