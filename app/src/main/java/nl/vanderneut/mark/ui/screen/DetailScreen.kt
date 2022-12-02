package nl.vanderneut.mark.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import nl.vanderneut.mark.NewsData
import nl.vanderneut.mark.R

import nl.vanderneut.mark.models.TopNewsArticle
import nl.vanderneut.mark.ui.MainViewModel

/**Todo 13: replace newsData with topNewsArticle and also replace the element values with data from it
 * Replace Image with CoilImage
 * For each Text we use elvis operator ?: to set the the value if its not null else set Not Available
 */
@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState,navController: NavController, mainViewModel: MainViewModel) {
    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = {navController.popBackStack()})
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FavoriteButton(article, mainViewModel)
            SubcomposeAsyncImage(
                model = article.urlToImage,
                contentScale = ContentScale.FillBounds,

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(Icons.Default.Edit, info = article.author?:"Not Available")
                InfoWithIcon(icon = Icons.Default.DateRange, info = article.author?:"not avail")
            }
            Text(text = article.title?:"Not Available", fontWeight = FontWeight.Bold)
            Text(text = article.description?:"Not Available", modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_500
            )
        )
        Text(text = info)
    }
}

@Composable
fun FavoriteButton(
    article: TopNewsArticle,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63)
) {
    var isFavorite = mainViewModel.favoriteArticles.contains(article)
    Log.d("favbutton: id", article.toString())

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
            if(isFavorite){
                mainViewModel.addFav(article)
            }
            else
            {
                mainViewModel.remove(article)
            }
        }

    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

////Todo 14: replace the preview data with TopNewsArticle
//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    DetailScreen(
//        TopNewsArticle(
//            author = "Namita Singh",
//            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
//            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
//            publishedAt = "2021-11-04T04:42:40Z"
//        ), rememberScrollState(),
//        rememberNavController()
//    )
//}