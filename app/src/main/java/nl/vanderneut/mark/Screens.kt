package nl.vanderneut.mark

enum class Screens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    NewsApp576598,
    DetailScreen,
    TopNews,
    FavoritesScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            NewsApp576598.name -> NewsApp576598
            DetailScreen.name -> DetailScreen
            TopNews.name -> TopNews
            FavoritesScreen.name -> FavoritesScreen
            null -> TopNews
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }


}