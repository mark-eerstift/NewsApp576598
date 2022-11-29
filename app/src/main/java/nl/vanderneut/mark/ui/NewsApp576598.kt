package nl.vanderneut.mark.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.vanderneut.mark.MockData
import nl.vanderneut.mark.ui.screen.DetailScreen
import nl.vanderneut.mark.ui.screen.TopNews

@Composable
fun NewsApp576598()
{
    Navigation()
}

@Composable
fun Navigation()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "TopNews"){
        composable("TopNews"){
            TopNews(navController = navController)
        }
        composable("Detail/{newsId}",
            arguments = listOf(navArgument("newsId"){
                type = NavType.IntType
            })){
            navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailScreen(navController = navController, newsData)
        }
    }
}