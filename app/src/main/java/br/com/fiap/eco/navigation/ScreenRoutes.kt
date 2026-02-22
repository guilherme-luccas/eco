package br.com.fiap.eco.navigation

sealed class Destination(val route: String) {
    object SignupScreen : Destination("signup")
    object LoginScreen : Destination("login")
    object HomeScreen : Destination("home/{email}") {
        fun createRoute(email: String): String = "home/$email"
    }
    object TipsScreen : Destination("tips/{email}") {
        fun createRoute(email: String): String = "tips/$email"
    }
    object ProfileScreen : Destination("profile/{email}") {
        fun createRoute(email: String): String = "profile/$email"
    }
}
