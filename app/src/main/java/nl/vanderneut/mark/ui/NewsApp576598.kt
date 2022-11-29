package nl.vanderneut.mark.ui

import androidx.compose.runtime.Composable
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        composable("Detail"){
            DetailScreen(navController = navController)
        }
    }
}