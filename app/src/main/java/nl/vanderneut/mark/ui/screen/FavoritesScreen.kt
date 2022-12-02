package nl.vanderneut.mark.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.components.ErrorUI
import nl.vanderneut.mark.components.LoadingUI
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


