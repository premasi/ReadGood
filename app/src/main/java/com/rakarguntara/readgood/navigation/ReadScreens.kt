package com.rakarguntara.readgood.navigation

enum class ReadScreens {
    SplashScreen,
    LoginScreen,
    RegisterScreen,
    HomeScreen,
    DetailScreen,
    UpdateScreen,
    StatsScreen,
    SearchScreen;
    companion object {
        fun fromRoute(route: String) : ReadScreens =
            when (route.substringBefore("/")){
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                RegisterScreen.name -> RegisterScreen
                HomeScreen.name -> HomeScreen
                DetailScreen.name -> DetailScreen
                UpdateScreen.name -> UpdateScreen
                StatsScreen.name -> UpdateScreen
                SearchScreen.name -> SearchScreen
                else -> HomeScreen
            }
    }
}