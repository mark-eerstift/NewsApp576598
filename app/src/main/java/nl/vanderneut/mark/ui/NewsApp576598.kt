package nl.vanderneut.mark.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.components.BottomMenu
import nl.vanderneut.mark.models.TopNewsArticle
import nl.vanderneut.mark.ui.screen.*

@Composable
fun NewsApp576598(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()

    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState, mainViewModel)

}


@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel
) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    Log.d("mainAct", "Getting Articles")
    val articles = viewModel.newsResponse.collectAsLazyPagingItems()
    Log.d("mainAct", "Got Articles")

    //articles.addAll(topArticles ?: listOf())
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.name,
        modifier = Modifier.padding(paddingValues)
    ) {
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
        composable(Screens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(Screens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }

        bottomNavigation(
            navController = navController,
            articles,
            viewModel = viewModel,
            isLoading = isLoading,
            isError = isError
        )

        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {


                val article = articles[index]
                if (article != null) {
                    DetailScreen(article, scrollState, navController, viewModel)
                }
            }
        }
        composable(Screens.TopNews.name) {
            TopNews(
                navController = navController, articles,
                viewModel = viewModel, isLoading = isLoading, isError = isError
            )
        }

        composable(Screens.FavoritesScreen.name) {
            FavoritesScreen(
                navController = navController,
                viewModel = viewModel, isLoading = isLoading, isError = isError
            )

        }


    }
}

fun NavGraphBuilder.bottomNavigation(
    navController: NavController, articles: LazyPagingItems<TopNewsArticle>,
    viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>
) {
    composable(Screens.TopNews.name) {

        TopNews(
            navController = navController, articles,
            viewModel = viewModel, isLoading = isLoading, isError = isError
        )

    }

    composable(Screens.FavoritesScreen.name) {
        FavoritesScreen(
            navController = navController,
            viewModel = viewModel, isLoading = isLoading, isError = isError
        )

    }


}