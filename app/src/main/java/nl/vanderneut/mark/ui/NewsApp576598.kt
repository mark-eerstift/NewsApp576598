package nl.vanderneut.mark.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import nl.vanderneut.mark.models.UserInfo
import nl.vanderneut.mark.ui.screen.Categories
import nl.vanderneut.mark.ui.screen.DetailScreen
import nl.vanderneut.mark.ui.screen.Sources
import nl.vanderneut.mark.ui.screen.TopNews

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

@Composable
fun Navigation(navController:NavHostController, scrollState: ScrollState, paddingValues: PaddingValues, viewModel: MainViewModel) {
    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {

        bottomNavigation(navController = navController, articles,viewModel = viewModel)
        composable("Detail/{index}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType }
            )) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index")
            index?.let {

                    articles.clear()
                    articles.addAll(topArticles?: listOf())

                val article = articles[index]
                DetailScreen(article, scrollState, navController)
            }
        }
    }
}

//Todo 19:create a query variable
fun NavGraphBuilder.bottomNavigation(navController: NavController, articles:List<TopNewsArticle>,
                                     viewModel: MainViewModel
) {
    composable(BottomMenuScreen.TopNews.route) {

        //Todo 20: replace newsManager with viewModel and pass in a query parameter
        TopNews(navController = navController,articles,viewModel = viewModel)
    }


//    composable(BottomMenuScreen.Sources.route) {
//        //Todo 13: pass in viewmodel as argument
//        //Sources(viewModel = viewModel)
//    }
}