package com.deliverytz.ui.navigation

/**
 * Список маршрутов навигации AuthNavHost
 */
sealed class Route(val route: String) {
    object CodeScreen: Route("code_screen")
    object AccountScreen: Route("account_screen")
    object LoginScreen: Route("login_screen")
    object DetailScreen: Route("detail_screen")
}