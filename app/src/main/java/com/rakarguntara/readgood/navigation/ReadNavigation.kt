package com.rakarguntara.readgood.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rakarguntara.readgood.screens.detail.DetailScreen
import com.rakarguntara.readgood.screens.home.HomeScreen
import com.rakarguntara.readgood.screens.login.LoginScreen
import com.rakarguntara.readgood.screens.search.SearchScreen
import com.rakarguntara.readgood.screens.splash.SplashScreen
import com.rakarguntara.readgood.screens.stats.StatsScreens
import com.rakarguntara.readgood.screens.update.UpdateScreen
import com.rakarguntara.readgood.viewmodel.home.HomeViewModel
import com.rakarguntara.readgood.viewmodel.search.SearchViewModel

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
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(navController, homeViewModel)
        }

        composable(ReadScreens.StatsScreen.name){
            val homeViewModel = hiltViewModel<HomeViewModel>()
            StatsScreens(navController, homeViewModel)
        }

        composable(ReadScreens.SearchScreen.name){
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController, searchViewModel)
        }

        composable("${ReadScreens.DetailScreen.name}/{bookId}", arguments =
            listOf(navArgument("bookId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let { id ->
                DetailScreen(navController = navController, bookId = id.toString())
            }
        }

        composable("${ReadScreens.UpdateScreen.name}/{bookItemId}",
            arguments = listOf(navArgument("bookItemId"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId").let { id ->
                UpdateScreen(navController, bookItemId = id)
            }
        }
    }
}