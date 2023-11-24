package nl.vanderneut.mark.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import nl.vanderneut.mark.R
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.SharedPreferencesHelper
import nl.vanderneut.mark.components.ErrorUI
import nl.vanderneut.mark.components.LoadingUI
import nl.vanderneut.mark.models.NewsItem
import nl.vanderneut.mark.ui.MainViewModel

private fun ArticleState(state: LoadState, modifier: Modifier = Modifier) {
    when (state) {
        is LoadState.Loading -> {
            //LoadingUI()
        }
        //is LoadState.Error -> ErrorUI()
        is LoadState.NotLoading -> {}
        else -> {}
    }
}

@Composable
fun TopNews(
    navController: NavController,
    articles: LazyPagingItems<NewsItem>,
    viewModel: MainViewModel,
    sharedPreferencesHelper: SharedPreferencesHelper // Pass SharedPreferencesHelper
) {
    val loadingState by viewModel.isLoading.collectAsState()
    val errorState by viewModel.isError.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            // Use the SharedPreferencesHelper to show the user's login status
            Text("Signed in")
            Button(onClick = {
                // Clear the token and navigate to SplashScreen
                sharedPreferencesHelper.clearAuthToken()
                navController.navigate(Screens.SplashScreen.name)
            }) {
                Text(stringResource(R.string.SignOutButton))
            }
            Button(onClick = { articles.refresh() }) {
                Text(text = stringResource(R.string.refrsh))
            }
        }

        val state = articles.loadState

        when {
            loadingState -> {
                LoadingUI()
                Log.d("topnews.kt", "loading now")
            }
            errorState -> {
                ErrorUI()
                Log.d("topnews.kt", "error ui")
            }
            else -> {
                LazyColumn {
                    item { ArticleState(state.prepend) }
                    items(articles.itemCount) { index ->
                        articles[index]?.let {
                            TopNewsItem(
                                it,
                                onNewsClick = { navController.navigate("Detail/$index") },
                                isFavorite = it.IsLiked // Pass the isFavorite property
                            )
                        }
                    }
                    item { ArticleState(state.append) }
                }

                ArticleState(state.refresh)
            }
        }
    }
}


@Composable
fun TopNewsItem(
    article: NewsItem,
    onNewsClick: () -> Unit = {},
    isFavorite: Boolean = false,
    modifier: Modifier = Modifier
        .padding(8.dp)
        .clickable {
            Log.d("TopNewsItem", "Article clicked: ${article.Title}")
            onNewsClick()
        },
) {
    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SubcomposeAsyncImage(
                    model = article.Image,
                    modifier = Modifier.size(100.dp),
                    contentDescription = "",
                ) {
                    // ... (unchanged)
                }
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = article.Title ?: stringResource(R.string.titlenotavail),
                        fontWeight = FontWeight.Bold
                    )
                }
                FavoriteIcon(isLiked = isFavorite)
            }
        }
    }
}

@Composable
fun FavoriteIcon(
    isLiked: Boolean,
    modifier: Modifier = Modifier
) {
    Icon(
        tint = if (isLiked) Color(0xffE91E63) else Color.Gray,
        modifier = modifier.size(40.dp),
        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = null
    )
}

