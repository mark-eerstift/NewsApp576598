package nl.vanderneut.mark

import java.lang.IllegalArgumentException

enum class Screens {
     SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    NewsApp576598,
    SearchScreen,
    DetailScreen,
    TopNews,
    ReaderStatsScreen;

    companion object {
         fun fromRoute(route: String?): Screens
          = when(route?.substringBefore("/")) {
              SplashScreen.name -> SplashScreen
             LoginScreen.name -> LoginScreen
             CreateAccountScreen.name -> CreateAccountScreen
             NewsApp576598.name -> NewsApp576598
             SearchScreen.name -> SearchScreen
             DetailScreen.name -> DetailScreen
             TopNews.name -> TopNews
             ReaderStatsScreen.name -> ReaderStatsScreen
             null -> TopNews
             else -> throw IllegalArgumentException("Route $route is not recognized")
          }
    }


}