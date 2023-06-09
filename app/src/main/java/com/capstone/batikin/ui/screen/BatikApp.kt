package com.capstone.batikin.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.batikin.R
import com.capstone.batikin.ui.navigation.NavigationItem
import com.capstone.batikin.ui.navigation.Screen
import com.capstone.batikin.ui.screen.main.profile.ProfileScreen
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.capstone.batikin.model.UserState
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.screen.camera.CameraApp
import com.capstone.batikin.ui.screen.detail.DetailApp
import com.capstone.batikin.ui.screen.main.history.HistoryScreen
import com.capstone.batikin.ui.screen.main.home.HomeScreen
import com.capstone.batikin.ui.screen.main.wishlist.WishlistScreen
import com.capstone.batikin.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BatikApp(
    modifier: Modifier = Modifier,
    userState: UserState,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val wishlistItems = listDummy

    // Simpan status apakah sedang membuka halaman Detail atau tidak
    val isDetailScreen = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailBatik.route) {
                BatikBottomBar(navController)
            }
        },
        modifier = modifier.background(Color.Transparent),
        floatingActionButton = {
            if (!isDetailScreen.value) { // Tampilkan FAB hanya jika bukan halaman Detail
                FloatingActionButton(
                    onClick = {
                        // Navigasi ke CameraScreen
                        navController.navigate(Screen.Camera.route)
                    },
                    backgroundColor = Color(0xFFFFA500),
                    contentColor = Color.White, // Warna ikon
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .background(Color.White)
        ) {
            // Set nilai status isDetailScreen sesuai dengan rute saat ini
            isDetailScreen.value = currentRoute == Screen.DetailBatik.route

            NavigationHost(navController = navController, userState, innerPadding)


        }
    }
}


@Composable
fun NavigationHost(
    navController: NavHostController,
    userState: UserState,
    innerPadding: PaddingValues
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val wishlistItems = listDummy

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController = navController, name = userState.name)
        }
        composable(Screen.History.route) {
            // Implementasi tampilan untuk History
            HistoryScreen()
        }
        composable(Screen.Wishlist.route) {
            // Implementasi tampilan untuk Wishlist
            val token = userState.token
            userState.id?.let { WishlistScreen(userState = userState, navController = navController) }
        }
        composable(Screen.Profile.route) {
            // Implementasi tampilan untuk Profile
            ProfileScreen(userState)
        }
        // Kamera klo gk pake FAB
        composable(Screen.Camera.route) {
            CameraApp(navController = navController, paddingValues = innerPadding)
        }
        //Implement detail
        composable(
            route = Screen.DetailBatik.route,
            arguments = listOf(
                navArgument("batikId") {
                    type = NavType.IntType
                }
            )
        ) {
            val batikId = it.arguments?.getInt("batikId")
            if (batikId != null) {
//                val navController = rememberNavController()
                val mainViewModel = viewModel<MainViewModel>()
                val token = userState.token

                // Panggil fungsi getBatikDetail di ViewModel untuk mendapatkan data detail batik
                mainViewModel.getBatikDetail(batikId)

                // Ambil data detail batik dari ViewModel
                val batikItem by mainViewModel.detailData.observeAsState()

                // Render DetailApp dengan data yang diperlukan
                DetailApp(id = batikId, navController = navController, token = token.toString(), photoUrl = batikItem?.photourl ?: "", price = batikItem?.price ?: 0, title = batikItem?.title ?: "", userState = userState)
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
        // Camera
//        NavigationItem(
//            title = stringResource(R.string.Camera),
//            icon = Icons.Default.Camera,
//            screen = Screen.Camera
//        ),
//        Spacer(modifier = Modifier.height(16.dp)),
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

    BottomAppBar(
        modifier = modifier
            .background(Color.Transparent),
//        cutoutShape = CutCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        cutoutShape = CircleShape,
        backgroundColor = Color.White

    ) {
//        navigationItems.forEachIndexed { index, item ->
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    if (currentRoute == item.screen.route) {
                        Text(item.title)
                    } else{
                        Text(item.title, color = Color.White)
                    }
                },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray
            )
            if (item.title == stringResource(R.string.menu_history)) {
                Spacer(modifier = Modifier.width(70.dp)) // Jarak yang berbeda untuk "History"
            } else if (item.title == stringResource(R.string.menu_wishlist)) {
                Spacer(modifier = Modifier.width(-30.dp)) // Jarak default untuk item lainnya
            }
        }
    }
}

