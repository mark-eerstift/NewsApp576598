package nl.vanderneut.mark.components


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import nl.vanderneut.mark.BottomMenuScreen
import nl.vanderneut.mark.R


@Composable
fun BottomMenu(navController: NavController) {

    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Favorites
    )
    NavigationBar(contentColor = colorResource(id = R.color.white))
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        menuItems.forEach {
            NavigationBarItem(
                label = { Text(text = it.title) },
                alwaysShowLabel = true,
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },

                )

        }
    }
}