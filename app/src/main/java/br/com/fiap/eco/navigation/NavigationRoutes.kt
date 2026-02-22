package br.com.fiap.eco.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.eco.screens.HomeScreen
import br.com.fiap.eco.screens.LoginScreen
import br.com.fiap.eco.screens.ProfileScreen
import br.com.fiap.eco.screens.SignupScreen
import br.com.fiap.eco.screens.TipsScreen

@Composable
fun NavigationRoutes() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.LoginScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Destination.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Destination.SignupScreen.route) {
            SignupScreen(navController)
        }
        composable(
            route = Destination.HomeScreen.route,
            arguments = listOf(
                navArgument(name = "email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            HomeScreen(
                navController,
                backStackEntry.arguments?.getString("email") ?: ""
            )
        }
        composable(
            route = Destination.TipsScreen.route,
            arguments = listOf(
                navArgument(name = "email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            TipsScreen(
                navController,
                backStackEntry.arguments?.getString("email") ?: ""
            )
        }
        composable(
            route = Destination.ProfileScreen.route,
            arguments = listOf(
                navArgument(name = "email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            ProfileScreen(
                navController,
                backStackEntry.arguments?.getString("email") ?: ""
            )
        }
    }
}
