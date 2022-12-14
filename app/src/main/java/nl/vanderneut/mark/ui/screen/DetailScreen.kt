package nl.vanderneut.mark.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import nl.vanderneut.mark.R
import nl.vanderneut.mark.models.TopNewsArticle
import nl.vanderneut.mark.ui.MainViewModel


@Composable
fun DetailScreen(
    article: TopNewsArticle,
    scrollState: ScrollState,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val uriHandler = LocalUriHandler.current
    var visible by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = { navController.popBackStack() })
    }) {

        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(
                // Enters by sliding down from offset -fullHeight to 0.
                initialOffsetY = { fullHeight -> -fullHeight }
            ),
            exit = slideOutVertically(
                // Exits by sliding up from offset 0 to -fullHeight.
                targetOffsetY = { fullHeight -> -fullHeight }
            )
        )
        {
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

                    contentDescription = stringResource(R.string.articleImageAlt),

                    ) {

                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator()

                    } else if (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty) {
                        Image(
                            painterResource(R.drawable.errorimg),
                            stringResource(R.string.imgErrorAlt)
                        )

                    } else {

                        SubcomposeAsyncImageContent()
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoWithIcon(
                        Icons.Default.Edit,
                        info = article.author ?: stringResource(R.string.AuthorNull)
                    )
                    InfoWithIcon(
                        icon = Icons.Default.DateRange,
                        info = article.publishedAt ?: stringResource(
                            R.string.DetailDateNotAvail
                        )
                    )
                }
                Text(
                    text = article.title ?: stringResource(R.string.detailTitleNotAvail),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = article.description ?: stringResource(R.string.detailDescNotAvail),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    modifier = Modifier.clickable { article.url?.let { it1 -> uriHandler.openUri(uri = it1) } },
                    text = article.url.toString()
                )
            }
        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = {
        Text(
            text = stringResource(R.string.DetailScrenTitle),
            fontWeight = FontWeight.SemiBold
        )
    },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.BackArrowAltText)
                )
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = stringResource(R.string.authorAltText),
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

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
            if (isFavorite) {
                mainViewModel.addFav(article)
            } else {
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

