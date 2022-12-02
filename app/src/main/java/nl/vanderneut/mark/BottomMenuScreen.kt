package nl.vanderneut.mark

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(val route: String, val icon: ImageVector, val title: String) {

    object TopNews : BottomMenuScreen("top news", icon = Icons.Outlined.Home, "Top News")
    object Favorites : BottomMenuScreen("Favorites", icon = Icons.Outlined.Bolt, "Favorites")
}
