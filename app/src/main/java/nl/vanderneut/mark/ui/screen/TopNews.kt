package nl.vanderneut.mark.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import nl.vanderneut.mark.MockData
import nl.vanderneut.mark.NewsData
import nl.vanderneut.mark.models.TopNewsArticle
import nl.vanderneut.mark.R

//Todo 4: create TopNewsArticle list variable and replace the list in items with the size instead
@Composable
fun TopNews(navController: NavController,articles:List<TopNewsArticle>) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Top News",fontWeight = FontWeight.SemiBold)
        LazyColumn{
            items(articles.size){index->
                //Todo 5: update newsData with article from each index
                TopNewsItem(article =articles[index],

                    onNewsClick = {   navController.navigate("Detail/$index")
                    Log.v("hello", "Onclick called")
                    }
                )
            }
        }
    }
}



@Composable
fun TopNewsItem(article: TopNewsArticle, modifier:Modifier = Modifier
    .padding(8.dp)
    .clickable {
        onNewsClick()
    }, onNewsClick: () -> Unit = {},){
    Card(modifier,border = BorderStroke(2.dp,color = colorResource(id = R.color.purple_500))) {
        Row(modifier.fillMaxWidth()) {
            SubcomposeAsyncImage(
                model = article.urlToImage,
                modifier = Modifier.size(100.dp),

                contentDescription = "",

            ) {

                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator()

                } else if (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty){
                    Image(painterResource(R.drawable.errorimg),"error loading image")

                }
                else
                {

                    SubcomposeAsyncImageContent()
                }
            }
            Column(modifier ) {
                Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold)
                Row {
                    Text(text = article.author?:"Not Available")
                }
            }
        }

    }
}

//Todo 3:Update preview to use TopNewsItem and remove id
@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(  TopNewsArticle(
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z"
    ))
}