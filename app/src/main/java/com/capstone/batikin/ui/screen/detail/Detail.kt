package com.capstone.batikin.ui.screen.detail

import android.content.Intent
import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
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
import com.capstone.batikin.ui.components.TopBarGeneral
import com.capstone.batikin.ui.screen.main.home.Item
import com.capstone.batikin.ui.screen.main.home.SectionHeader
import com.capstone.batikin.ui.screen.payment.PaymentActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.capstone.batikin.viewmodel.MainViewModel

@Composable
fun DetailApp(id: Int, navController: NavController, token: String, photoUrl: String, price: Int, title: String, userState: UserState) {
    var isExpanded by remember { mutableStateOf(false) }
    var loadingDone by remember { mutableStateOf(false) }
    var imageAlpha by remember { mutableStateOf(1f) }

    val context = LocalContext.current

    //buat tambah wishlist
    var isAddedToWishlist by remember { mutableStateOf(false) }

    val mainViewModel = viewModel<MainViewModel>()
    mainViewModel.getBatikDetail(id)
    mainViewModel.getBatikList()

    userState.token?.let {
        userState.id?.let { it1 ->
            mainViewModel.getWishlist(it, it1)
        }
    }

    val wishlistCheckData by mainViewModel.wishlistData.observeAsState()

    val wishlistCheckArray = wishlistCheckData?.filter { it.id == id }

    isAddedToWishlist = wishlistCheckArray?.isNotEmpty() ?: false

    val batikItem by mainViewModel.detailData.observeAsState()
    val batikListLive by mainViewModel.listData.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState()
    val batikList = arrayListOf<Batik>()
    batikListLive?.filter { it?.id != batikItem?.id }?.map {
        batikList.add(Batik(it!!.id, it.title, it.photourl, it.price, it.description,
            it.rating as Double
        ))
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(batikItem?.photourl)
            .size(Size.ORIGINAL)
            .build()
    )

    loadingDone = painter.state !is AsyncImagePainter.State.Loading

    Column(modifier = Modifier.fillMaxSize()) {
        TopBarDetail(
            batikTitle = batikItem?.title ?: "",
            painter = painter,
            onBackClicked = { navController.navigateUp() }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            Box(modifier = Modifier.alpha(imageAlpha)) {
                if (loadingDone) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
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
                            //masukin wishlist
                            if (!isAddedToWishlist) {
                                mainViewModel.addWishlist(context, token, id, photoUrl, price, title)
                                isAddedToWishlist = true
                            } else {
                                mainViewModel.deleteWishlist(context, token, id)
                                isAddedToWishlist = false
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
                    text = stringResource(R.string.deskripsi),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (batikItem != null) {
                    batikItem?.description?.let {
                        Text(
                            text = it,
                            maxLines = if (isExpanded) 20 else 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (!isExpanded) stringResource(R.string.lihat_semua) else stringResource(R.string.sembunyikan_semua),
                    modifier = Modifier
                        .clickable {
                            isExpanded = !isExpanded
                        },
                    color = colorResource(id = R.color.orange_light)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        modifier = Modifier
                            .width(170.dp)
                            .padding(start = 8.dp, end = 8.dp)

                    ) {
                        Text(
                            text = stringResource(R.string.plus_keranjang),
                            color = colorResource(id = R.color.orange_light),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
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
                            text = stringResource(R.string.beli_langsung),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Row dibawah tombol beli

                Row(horizontalArrangement = Arrangement.Center) {
                    if(isLoading == true){
                        CircularProgressIndicator()
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                SectionHeader(text = "Discover More")
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(batikList.take(5), key = {it.id}) {
                        Item(
                            item = it,
                            navController = navController as NavHostController,
                            modifier = Modifier
                                .width(140.dp))
                    }
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState) {
        scrollState.animateScrollTo(0)
    }

    val alphaValue = 1f - (scrollState.value / 200f)
    imageAlpha = alphaValue.coerceIn(0f, 1f)
}


@Composable
fun TopBarDetail(
    batikTitle: String,
    painter: Painter?,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    val orangeColor = Color(0xFFFFA500)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = "Batik Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        TopAppBar(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onBackClicked() }
                        .background(Color(0xFFFFA500), shape = CircleShape)
                )
                Text(
                    text = batikTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier
                        .background(
                            Color(0xFFFFA500),
                            shape = CutCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            )
                        )
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}




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