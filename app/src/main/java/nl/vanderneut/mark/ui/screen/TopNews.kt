package nl.vanderneut.mark.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import nl.vanderneut.mark.NewsData

@Composable
fun TopNews(navController: NavController){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Top News", fontWeight = FontWeight.SemiBold)
        Button(onClick = { navController.navigate("Detail") }) {
            Text(text = "go to detailsscreen")
        }
    }
}

//Composable for individual news item
@Composable
fun TopNewsItem(newsData: NewsData)
{
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp))
    {
        Image(painter = painterResource(id = newsData.image),
            contentDescription = "image",
        contentScale = ContentScale.FillBounds)
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        
        ) {
            Text(text = newsData.publishedAt, 
               color = Color.White, 
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = newsData.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview()
{
    //TopNews(rememberNavController())
    TopNewsItem(newsData = NewsData(
        1,
        author = "Raja Razek, CNN",
        title = "'Tiger King' Joe Exotic says he has been diagnosed with aggressive form of prostate cancer - CNN",
        description = "Joseph Maldonado, known as Joe Exotic on the 2020 Netflix docuseries \\\"Tiger King: Murder, Mayhem and Madness,\\\" has been diagnosed with an aggressive form of prostate cancer, according to a letter written by Maldonado.",
        publishedAt = "2021-11-04T05:35:21Z"
    ))
}