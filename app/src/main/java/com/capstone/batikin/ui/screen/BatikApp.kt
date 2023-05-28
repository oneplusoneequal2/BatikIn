package com.capstone.batikin.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.batikin.R
import com.capstone.batikin.ui.navigation.NavigationItem
import com.capstone.batikin.ui.navigation.Screen
import com.capstone.batikin.ui.screen.main.profile.ProfileScreen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.screen.detail.DetailApp
import com.capstone.batikin.ui.screen.main.home.HomeScreen
import com.capstone.batikin.ui.screen.main.wishlist.WishlistScreen

@Composable
fun BatikApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailBatik.route) {
                BatikBottomBar(navController)
            }
        },
        modifier = modifier
    )
    // batas copas yang ad fab
//    Scaffold(
//        bottomBar = {
//            BottomAppBar(
//                modifier = Modifier.fillMaxWidth(),
//                backgroundColor = Color.White,
//                cutoutShape = CircleShape
//            ) {
//                BatikBottomBar(navController = navController)
//            }
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    // Navigasi ke CameraScreen
////                    navController.navigate(Screen.Camera.route)
//                },
//                // disini kena error BoxScopeInstance
////                modifier = Modifier.align(Alignment.Center)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.CameraAlt,
//                    contentDescription = stringResource(R.string.camera_icon)
//                )
//            }
//        },
//        isFloatingActionButtonDocked = true,
//        floatingActionButtonPosition = FabPosition.Center,
//        modifier = modifier
//    )
    { innerPadding ->
        val wishlistItems = listDummy
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.History.route) {
                // Implementasi tampilan untuk History
//                HistoryScreen()
            }
            composable(Screen.Wishlist.route) {
                // Implementasi tampilan untuk Wishlist
                WishlistScreen(wishlistItems = wishlistItems, navController = navController)
            }
            composable(Screen.Profile.route) {
                // Implementasi tampilan untuk Profile
                ProfileScreen()
            }
            //Implement detail
            composable(
                route = Screen.DetailBatik.route,
                arguments = listOf(
                    navArgument("batikId"){
                        type = NavType.IntType
                    }
                )
            ){
                val batikId = it.arguments?.getInt("batikId")
                if (batikId != null) {
                    DetailApp(id = batikId, navController)
                }
            }
        }
    }

}


@Composable
fun BatikBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_home),
            icon = Icons.Default.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.menu_history),
            icon = Icons.Default.History,
            screen = Screen.History
        ),
        NavigationItem(
            title = stringResource(R.string.menu_wishlist),
            icon = Icons.Default.Favorite,
            screen = Screen.Wishlist
        ),
        NavigationItem(
            title = stringResource(R.string.menu_profile),
            icon = Icons.Default.AccountCircle,
            screen = Screen.Profile
        )
    )

    BottomNavigation(
        modifier = modifier
    ) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

