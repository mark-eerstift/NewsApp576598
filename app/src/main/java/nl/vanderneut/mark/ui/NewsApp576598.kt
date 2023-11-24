package nl.vanderneut.mark.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.SharedPreferencesHelper
import nl.vanderneut.mark.api.Repository
import nl.vanderneut.mark.components.BottomMenu
import nl.vanderneut.mark.models.NewsItem
import nl.vanderneut.mark.ui.screen.*

@Composable
fun NewsApp576598(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    // Access the Repository instance from the MainViewModel
    val repository = mainViewModel.repository

    // Pass the Repository to the MainScreen composable
    MainScreen(navController = navController, scrollState, mainViewModel, repository)
}

@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    mainViewModel: MainViewModel,
    repository: Repository
) {
    Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(
            navController = navController,
            scrollState = scrollState,
            paddingValues = it,
            viewModel = mainViewModel,
            repository = repository
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    repository: Repository
){
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val applicationContext = LocalContext.current
    val sharedPreferencesHelper = SharedPreferencesHelper(applicationContext)


    Log.d("mainAct", "Getting Articles")
    val articles = viewModel.newsResponse.collectAsLazyPagingItems()
    Log.d("mainAct", "Got Articles")

    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.name,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screens.SplashScreen.name) {
            SplashScreen(navController = navController, sharedPreferencesHelper = sharedPreferencesHelper)
        }

        composable(Screens.LoginScreen.name) {
            LoginScreen(navController = navController, repository = repository, sharedPreferencesHelper = sharedPreferencesHelper)
        }

        bottomNavigation(
            navController = navController,
            articles = articles,
            viewModel = viewModel,
            sharedPreferencesHelper
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
                navController = navController, articles = articles,
                viewModel = viewModel, sharedPreferencesHelper = sharedPreferencesHelper
            )
        }

        composable(Screens.FavoritesScreen.name) {
            FavoritesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
fun NavGraphBuilder.bottomNavigation(
    navController: NavController,
    articles: LazyPagingItems<NewsItem>,
    viewModel: MainViewModel,
    sharedPreferencesHelper: SharedPreferencesHelper
) {
    composable(Screens.TopNews.name) {
        TopNews(
            navController = navController,
            articles = articles,
            viewModel = viewModel,
            sharedPreferencesHelper
        )
    }

    composable(Screens.FavoritesScreen.name) {
        FavoritesScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}
