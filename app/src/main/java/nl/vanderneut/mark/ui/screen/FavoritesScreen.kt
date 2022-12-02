package nl.vanderneut.mark.ui.screen

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
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



@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MainViewModel, isError: MutableState<Boolean>, isLoading: MutableState<Boolean>
) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {

        Text("signed in user: " + FirebaseAuth.getInstance().currentUser?.email)
        Button(onClick = {
            FirebaseAuth.getInstance().signOut().run{
                navController.navigate(Screens.SplashScreen.name)
            }
        }) {
            Text("sign out")
        }


        when{
            isLoading.value ->{
                LoadingUI()
            }
            isError.value ->{
                ErrorUI()
            }else ->{


            LazyColumn {
                items(viewModel.favoriteArticles.size) { index ->
                    TopNewsItem(article = viewModel.favoriteArticles[index],
                        onNewsClick = { navController.navigate("Detail/$index") }
                    )
                }
            }
                }
            }


            }


        }


