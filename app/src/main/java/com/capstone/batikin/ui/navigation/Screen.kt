package com.capstone.batikin.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object History : Screen("history")
    object Wishlist : Screen("wishlist")
    object Profile : Screen("profile")
    object Camera : Screen("camera")
    object DetailBatik : Screen("detail/{batikId}") {
        fun createRoute(batikId: Int) = "detail/$batikId"
    }
}