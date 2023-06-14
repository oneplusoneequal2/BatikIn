package com.capstone.batikin.ui.screen.main.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.capstone.batikin.model.Batik
import com.capstone.batikin.R
import com.capstone.batikin.model.UserState
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.components.TopBarGeneral
import com.capstone.batikin.ui.navigation.Screen
import com.capstone.batikin.viewmodel.MainViewModel


@Composable
fun WishlistScreen(modifier: Modifier = Modifier, navController: NavHostController, userState: UserState) {
    val mainViewModel = viewModel<MainViewModel>()
    val context = LocalContext.current
    val wishlist by mainViewModel.wishlistData.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState()

    LaunchedEffect(key1 = wishlist) {
        userState.token?.let {
            userState.id?.let { it1 ->
                mainViewModel.getWishlist(it, it1)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBarGeneral(
            titleResId = R.string.title_wishlist
        )
        if (isLoading == true) {
            CircularProgressIndicator()
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
        ) {
            wishlist?.let { it ->
                items(it.filter { it.id != 0 }, key = { it.id}) { item ->
                    WishlistItem(
                        item = Batik(item.id, item.title, item.photourl, item.price, item.description, item.rating as Double, true),
                        navController = navController, onDelete = {
                            userState.token?.let {
                                mainViewModel.deleteWishlist(context, it, item.id)
                            }
                        })
                }
            }

        }

    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WishlistItem(item: Batik, navController: NavHostController, onDelete: () -> Unit) {

    val orangeColor = Color(0xFFFFA500)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = {navController.navigate(Screen.DetailBatik.createRoute(batikId = item.id))}
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.photoUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Rp. ${item.price}",
                    style = MaterialTheme.typography.body2,
                    color = orangeColor
                )
            }
            IconButton(
                onClick = { /* hapus dari wishlist */ },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Remove from wishlist",
                    tint = Color.Red,
                    modifier = Modifier.clickable(true, onClick = onDelete)
                )
            }
        }
    }
}


@Preview
@Composable
fun WishlistScreenPreview() {
    val wishlistItems = listDummy
    var navController: NavHostController = rememberNavController()

//    WishlistScreen(wishlistItems = wishlistItems, navController = navController, userId = 1, token = token)
}
