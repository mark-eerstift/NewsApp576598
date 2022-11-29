package nl.vanderneut.mark.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.vanderneut.mark.NewsData
import nl.vanderneut.mark.R

@Composable
fun DetailScreen(newsData: NewsData, scrollState: ScrollState){
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Details", fontWeight = FontWeight.SemiBold)
        Image(painter = painterResource(id = newsData.image), contentDescription = "")
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween){
            InfoWithIcon(Icons.Default.Edit, info = newsData.author)
            InfoWithIcon(Icons.Default.DateRange, info = newsData.publishedAt)

        }

        Text(text = newsData.title, fontWeight = FontWeight.Bold)
        Text(text = newsData.description, modifier = Modifier.padding(top=16.dp))
    }
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String)
{
    Row{
        Icon(icon, contentDescription = "Author",
            modifier = Modifier.padding(end=8.dp),
        colorResource(id = R.color.purple_500)
            )
        Text(text = info)
    }
}

@Preview()
@Composable
fun DetailsScreenPreview()
{
    DetailScreen(NewsData(
        1,
        author = "Raja Razek, CNN",
        title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
        description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
        publishedAt = "2021-11-04T05:35:21Z"
    ), rememberScrollState())
}