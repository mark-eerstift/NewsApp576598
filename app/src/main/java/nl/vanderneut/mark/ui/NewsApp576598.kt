package nl.vanderneut.mark.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.vanderneut.mark.BottomMenuScreen
import nl.vanderneut.mark.MockData
import nl.vanderneut.mark.api.NewsManager
import nl.vanderneut.mark.components.BottomMenu
import nl.vanderneut.mark.models.TopNewsArticle
import nl.vanderneut.mark.ui.screen.Categories
import nl.vanderneut.mark.ui.screen.DetailScreen
import nl.vanderneut.mark.ui.screen.Sources
import nl.vanderneut.mark.ui.screen.TopNews

@Composable
fun NewsApp576598() {
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController,scrollState)
}

@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        //Todo 10: use the padding value from scaffold as a value in NavHost
        Navigation(navController =navController , scrollState =scrollState,paddingValues = it )
    }
}

//Todo 9: create a padding value variable and pass into NavHost modifier
@Composable
fun Navigation(navController:NavHostController,scrollState: ScrollState,newsManager: NewsManager= NewsManager(),paddingValues: PaddingValues) {
    Log.v("Navigation", "onclick calls Navigation in 576598")
    val articles = newsManager.newsResponse.value.articles
    //Log.d("newss","$articles")
    articles?.let {
        NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {
            //Todo 7:pass articles to bottomNavigation
            bottomNavigation(navController = navController, articles)
            //Todo 12: replace the key with index and get article by selected index
            composable("Detail/{index}",
                arguments = listOf(
                    navArgument("index") { type = NavType.IntType }
                )) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }
        ?: run {
            //If articles IS null
            CircularProgressIndicator()
        }
}

//Todo 6: create TopNews list and provide the value to TopNews composable
fun NavGraphBuilder.bottomNavigation(navController: NavController,articles:List<TopNewsArticle>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController,articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}