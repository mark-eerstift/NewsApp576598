package nl.vanderneut.mark.components

import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import nl.vanderneut.mark.BottomMenuScreen
import nl.vanderneut.mark.R

@Composable
fun BottomMenu(navController: NavController){
    val menuItems = listOf(BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources)

    BottomNavigation(contentColor = colorResource(id = R.color.white)) {

    }
}