package nl.vanderneut.mark.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.firebase.auth.FirebaseAuth
import nl.vanderneut.mark.R
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.components.ErrorUI
import nl.vanderneut.mark.components.LoadingUI
import nl.vanderneut.mark.models.TopNewsArticle
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
    navController: NavController, articles: LazyPagingItems<TopNewsArticle>,
    viewModel: MainViewModel, isError: MutableState<Boolean>, isLoading: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(stringResource(R.string.HomeSignedIn) + FirebaseAuth.getInstance().currentUser?.email)
            Button(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(Screens.SplashScreen.name)
                }
            }) {
                Text(stringResource(R.string.SignOutButton))

            }
            Button(onClick = { articles.refresh() }) {
                Text(text = stringResource(R.string.refrsh))
            }
        }

        val state = articles.loadState

        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }
            else -> {


                LazyColumn {


                    item { ArticleState(state.prepend) }
                    items(articles.itemCount) { index ->
                        articles[index]?.let {
                            TopNewsItem(
                                it,
                                onNewsClick = { navController.navigate("Detail/$index") })
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
    article: TopNewsArticle,
    modifier: Modifier = Modifier
        .padding(8.dp)
        .clickable {
            //onNewsClick()
        },
    onNewsClick: () -> Unit = {},
) {
    Card(modifier, border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))) {
        Row(modifier.fillMaxWidth()) {
            SubcomposeAsyncImage(
                model = article.urlToImage,
                modifier = Modifier.size(100.dp),

                contentDescription = "",

                ) {

                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator()

                } else if (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty) {
                    Image(painterResource(R.drawable.errorimg), "error loading image")

                } else {

                    SubcomposeAsyncImageContent()
                }
            }
            Column(modifier) {
                Text(
                    text = article.title ?: stringResource(R.string.titlenotavail),
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Text(text = article.author ?: stringResource(R.string.authornotavail))
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        )
    )
}