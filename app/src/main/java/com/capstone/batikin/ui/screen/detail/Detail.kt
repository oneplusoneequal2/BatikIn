package com.capstone.batikin.ui.screen.detail

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.capstone.batikin.R
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.UserState
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.screen.payment.PaymentActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.capstone.batikin.viewmodel.MainViewModel

@Composable
fun DetailApp(id: Int, navController: NavController, token: String, photoUrl: String, price: Int, title: String, userState: UserState) {
    var isExpanded by remember { mutableStateOf(false) }
    var loadingDone by remember { mutableStateOf(false) }

    val context = LocalContext.current

    //buat tmbh wishlist
    var isAddedToWishlist by remember { mutableStateOf(false) }

    val mainViewModel = viewModel<MainViewModel>()
    mainViewModel.getBatikDetail(id)

    userState.token?.let {
        userState.id?.let { it1 ->
            mainViewModel.getWishlist(it, it1)
        }
    }

    val wishlistCheckData by mainViewModel.wishlistData.observeAsState()

    val wishlistCheckArray = wishlistCheckData?.filter { it.id == id }


    isAddedToWishlist = wishlistCheckArray?.isNotEmpty() ?: false


//    mainViewModel.addWishlist(token, id, photoUrl, price, title)

    val batikItem by mainViewModel.detailData.observeAsState()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(batikItem?.photourl)
            .size(Size.ORIGINAL)
            .build()
    )

    loadingDone = painter.state !is AsyncImagePainter.State.Loading

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Box() {
            if (loadingDone) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navController.navigateUp() }
                    .background(colorResource(id = R.color.orange_light), shape = CircleShape)
            )
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (!loadingDone) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(200.dp)) {
                Text(
                    text = "Rp. ${batikItem?.price}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                FloatingActionButton(
                    onClick = {
                              //maskuin wishlist
                        if (!isAddedToWishlist) {
                            userState.id?.let {
                                mainViewModel.addWishlist(token,
                                    it, photoUrl, price, title)
                            }
                            isAddedToWishlist = true
                        } else {
                            // Tindakan lain jika item sudah ada dalam Wishlist
                        }
                    },
                    backgroundColor = colorResource(id = R.color.orange_light),
                    contentColor = Color.White,
                    modifier = Modifier
                        .width(34.dp)
                        .height(34.dp)
                ) {
                    Icon(
                        painter = painterResource(id = if (isAddedToWishlist) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            batikItem?.title?.let {
                Text(
                    text = it
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Deskripsi",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (batikItem != null) {
                batikItem?.description?.let {
                    Text(
                        text = it,
                        maxLines = if (isExpanded) 10 else 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (!isExpanded) "Lihat Semua" else "Sembunyikan Semua",
                modifier = Modifier
                    .clickable {
                        isExpanded = !isExpanded
                    },
                color = colorResource(id = R.color.orange_light)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            Row(horizontalArrangement = Arrangement.Center,) {
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier
                        .width(170.dp)
                        .padding(start = 8.dp, end = 8.dp)

                ) {
                    Text(
                        text = "+ Keranjang",
                        color = colorResource(id = R.color.orange_light),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,

                    )
                }
                Button(
                    onClick = {
                        val intent = Intent(context, PaymentActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orange_light)),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = "Beli Langsung",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

}


//@Composable
//fun DetailApp(
//    item: Batik,
//    navController: NavHostController
//) {
//    var isExpanded by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
//        Box(modifier = Modifier.fillMaxWidth()) {
//            Image(
//                painter = rememberCoilPainter(item.photoUrl),
//                contentDescription = null,
//                modifier = Modifier.aspectRatio(1f)
//            )
//        }
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Spacer(modifier = Modifier.height(10.dp))
//            Row(horizontalArrangement = Arrangement.spacedBy(200.dp)) {
//                Text(
//                    text = "Rp. ${item.price}",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                FloatingActionButton(
//                    onClick = {},
//                    backgroundColor = colorResource(id = R.color.orange_light),
//                    contentColor = Color.White,
//                    modifier = Modifier
//                        .width(34.dp)
//                        .height(34.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.baseline_favorite_border_24),
//                        contentDescription = null
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = item.title
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = "Deskripsi",
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = item.desc,
//                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
//                overflow = TextOverflow.Ellipsis
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Text(
//                text = if (!isExpanded) "Lihat Semua" else "Sembunyikan Semua",
//                modifier = Modifier
//                    .clickable {
//                        isExpanded = !isExpanded
//                    },
//                color = colorResource(id = R.color.orange_light)
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//            Divider()
//            Row(horizontalArrangement = Arrangement.Center) {
//                Button(
//                    onClick = {
//                        val intent = Intent(LocalContext.current, PaymentActivity::class.java)
//                        LocalContext.current.startActivity(intent)
//                    },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
//                    modifier = Modifier.width(150.dp)
//                ) {
//                    Text(
//                        text = "Beli Langsung",
//                        color = colorResource(id = R.color.orange_light),
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//                Button(
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orange_light)),
//                    modifier = Modifier.width(150.dp)
//                ) {
//                    Text(
//                        text = "+Keranjang",
//                        color = Color.White,
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun DetailAppPreview() {
    var navController : NavHostController = rememberNavController()
    BatikInTheme {
//        DetailApp(
//            1,
//            navController
//        )
    }
}