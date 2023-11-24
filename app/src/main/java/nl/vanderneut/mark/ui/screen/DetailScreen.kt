package nl.vanderneut.mark.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import nl.vanderneut.mark.models.NewsItem
import nl.vanderneut.mark.ui.MainViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
//TODO: fix this suppressed error,
@Composable
fun DetailScreen(
    article: NewsItem,
    scrollState: ScrollState,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = { DetailTopAppBar(onBackPressed = { navController.popBackStack() }) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {


                Text("Before FavoriteButton") // Add this line
                FavoriteButton(article, mainViewModel, Modifier.align(Alignment.End))
                Text("After FavoriteButton") // Add this line


                Spacer(modifier = Modifier.height(16.dp))

                SubcomposeAsyncImage(
                    model = article.Image,
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

                Spacer(modifier = Modifier.height(16.dp))

                InfoWithIcon(
                    icon = Icons.Default.DateRange,
                    info = article.PublishDate ?: stringResource(R.string.DetailDateNotAvail)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = article.Title ?: stringResource(R.string.detailTitleNotAvail),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = article.Summary ?: stringResource(R.string.detailDescNotAvail),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.clickable {
                        article.Url?.let { uriHandler.openUri(uri = it) }
                    },
                    text = article.Url.toString()
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
    article: NewsItem,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    color: Color = Color(0xffE91E63)
) {
    var isFavorite = mainViewModel.favoriteArticles.contains(article)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = {
                isFavorite = !isFavorite
                if (isFavorite) {
                    mainViewModel.addFav(article)
                } else {
                    mainViewModel.remove(article)
                }
            },
        ) {
            Icon(
                tint = color,
                modifier = Modifier.graphicsLayer {
                    scaleX = 1.3f
                    scaleY = 1.3f
                },
                imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Outlined.FavoriteBorder
                },
                contentDescription = null
            )
        }
    }
}