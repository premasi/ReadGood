package com.rakarguntara.readgood.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rakarguntara.readgood.screens.home.HomeScreen
import com.rakarguntara.readgood.screens.login.LoginScreen
import com.rakarguntara.readgood.screens.splash.SplashScreen
import com.rakarguntara.readgood.screens.stats.StatsScreens

@Composable
fun ReadNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReadScreens.SplashScreen.name){
        composable(ReadScreens.SplashScreen.name){
            SplashScreen(navController)
        }

        composable(ReadScreens.LoginScreen.name){
            LoginScreen(navController)
        }

        composable(ReadScreens.HomeScreen.name){
            HomeScreen(navController)
        }

        composable(ReadScreens.StatsScreen.name){
            StatsScreens(navController)
        }
    }
}