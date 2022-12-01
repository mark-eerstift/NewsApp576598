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
import nl.vanderneut.mark.BottomMenuScreen
import nl.vanderneut.mark.Screens
import nl.vanderneut.mark.components.BottomMenu
import nl.vanderneut.mark.models.TopNewsArticle
import nl.vanderneut.mark.ui.screen.*

@Composable
fun NewsApp576598(mainViewModel: MainViewModel) {
    val scrollState = rememberScrollState()

    val navController = rememberNavController()
    MainScreen(navController = navController,scrollState, mainViewModel)

}



@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState,mainViewModel: MainViewModel) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it,viewModel = mainViewModel )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, paddingValues: PaddingValues, viewModel: MainViewModel) {
    Log.d("576598 says:", "Navigation function now")
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles

    Log.d("Toparticles:", topArticles.toString())
    Log.d("articles:", articles.toString())

    articles.addAll(topArticles ?: listOf())
    NavHost(navController = navController, startDestination =Screens.SplashScreen.name,modifier = Modifier.padding(paddingValues)) {
        Log.d("navhost says:", "inside navhost now")
        val isLoading = mutableStateOf(loading)
        val isError = mutableStateOf(error)
        composable(Screens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        Log.d("navhost says:", "done with composable one")
        composable(Screens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        Log.d("navhost says:", "done with composable two")
        bottomNavigation(navController = navController, articles,viewModel = viewModel, isLoading = isLoading, isError = isError)
        Log.d("navhost says:", "done with bottom nav")
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {

                    articles.clear()
                    articles.addAll(topArticles?: listOf())

                val article = articles[index]
                DetailScreen(article, scrollState, navController, viewModel)
            }
        }
        composable(Screens.TopNews.name) {
            TopNews(navController = navController,articles,viewModel = viewModel, isLoading = isLoading, isError = isError)
        }

    }
}

//Todo 19:create a query variable
fun NavGraphBuilder.bottomNavigation(navController: NavController, articles:List<TopNewsArticle>,
                                     viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>
) {
    composable(BottomMenuScreen.TopNews.route) {
        Log.d("navhost says:", "navgraph making")
        //Todo 20: replace newsManager with viewModel and pass in a query parameter
        TopNews(navController = navController,articles,viewModel = viewModel, isLoading = isLoading, isError = isError)
        Log.d("navhost says:", "navgraph done")
    }


//    composable(BottomMenuScreen.Sources.route) {
//        //Todo 13: pass in viewmodel as argument
//        //Sources(viewModel = viewModel)
//    }
}