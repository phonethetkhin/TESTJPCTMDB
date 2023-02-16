package com.ptk.testjpctmdb.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ptk.testjpctmdb.ui.screen.DetailScreen
import com.ptk.testjpctmdb.ui.screen.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Routes.DetailScreen.route + "?isFav={isFav}&movieId={movieId}",
            arguments = listOf(
                navArgument("isFav") {
                    type = NavType.BoolType
                },
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )) { nav ->
            val isFav = nav.arguments?.getBoolean("isFav")!!
            val movieId = nav.arguments?.getInt("movieId")!!

            DetailScreen(movieId = movieId, isFav = isFav, navController = navController)
        }
    }
}