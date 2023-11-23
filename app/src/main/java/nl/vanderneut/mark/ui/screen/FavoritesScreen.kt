package nl.vanderneut.mark.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import nl.vanderneut.mark.R
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.components.ErrorUI
import nl.vanderneut.mark.components.LoadingUI
import nl.vanderneut.mark.ui.MainViewModel


@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val loadingState by viewModel.isLoading.collectAsState()
    val errorState by viewModel.isError.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(R.string.SignedInUserName) + FirebaseAuth.getInstance().currentUser?.email)
        Button(onClick = {
            FirebaseAuth.getInstance().signOut().run {
                navController.navigate(Screens.SplashScreen.name)
            }
        }) {
            Text(stringResource(R.string.signOutButton))
        }

        when {
            loadingState -> {
                LoadingUI()
            }
            errorState -> {
                ErrorUI()
            }
            else -> {
                LazyColumn {
                    items(viewModel.favoriteArticles.size) { index ->
                        TopNewsItem(
                            article = viewModel.favoriteArticles[index],
                            onNewsClick = { navController.navigate("Detail/$index") }
                        )
                    }
                }
            }
        }
    }
}



