package nl.vanderneut.mark.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.vanderneut.mark.BottomMenuScreen
import nl.vanderneut.mark.MockData
import nl.vanderneut.mark.components.BottomMenu
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

//Todo 1: create a mainScreen composable
@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState) {
    Scaffold(bottomBar ={
        BottomMenu(navController = navController)
    }) {
        Navigation(navController =navController , scrollState =scrollState )
    }
}


@Composable
fun Navigation(navController:NavHostController,scrollState: ScrollState) {
    NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route) {
        bottomNavigation(navController = navController)
        composable("Detail/{newsId}",
            arguments = listOf(
                navArgument("newsId") { type = NavType.IntType }
            )){navBackStackEntry->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(newsData,scrollState,navController)
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavController) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController = navController)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}