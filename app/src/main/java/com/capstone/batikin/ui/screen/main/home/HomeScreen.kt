package com.capstone.batikin.ui.screen.main.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.categoryDummy
import com.capstone.batikin.ui.components.TopBar
import com.capstone.batikin.viewmodel.MainViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.capstone.batikin.R
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavHostController, name: String?, modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }

    val mainViewModel = viewModel<MainViewModel>()
    mainViewModel.getBatikList()
    val dataList by mainViewModel.listData.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState()

    val data = ArrayList<Batik>()

    dataList?.map {
        if (it != null) {
            data.add(Batik(it.id, it.title, it.photourl, it.price, it.description,
                it.rating as Double
            ))
        }
    }

    Scaffold(
        topBar = {
            TopBar(query) { query = it }
         },

        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
//        modifier = Modifier.background(Color.Transparent),
//        backgroundColor = Color.Transparent// Mengatur latbel Scaffold jadi transparan
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
//                .padding(start = 8.dp, top = 16.dp, end = 8.dp)
//                .background(Color.Transparent)
        ) {
            if (isLoading == true){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            if (query !== "") {
                SearchHome(query = query, data, navController)
            } else {
                HomeContent(data, navController, name = name)
            }
        }
    }
}

@Composable
fun SearchHome(query: String, data: ArrayList<Batik>, navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .height(600.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp) // Atur jarak antara item
    ) {
        items(data.filter { it.title.lowercase().contains(query.lowercase())}, key = { item -> item.id }) { item ->
            Item(item = item, navController = navController, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun HomeContent(data: ArrayList<Batik>, navController: NavHostController, name: String?) {

    HomeBanner(name)

    Spacer(modifier = Modifier.height(8.dp))

    SectionHeader(text = "Batik Categories")
    LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(categoryDummy) { category ->
            CategoryItem(title = category)
        }
    }

    SectionHeader(text = "Recommendation")
    LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(data.filter { it.rating >= 4.2}, key = { item -> item.id }) { item ->
            Item(
                item = item,
                navController = navController,
                modifier = Modifier
                    .width(140.dp)
            )
        }
    }

    SectionHeader(text = "Discover More")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .height(570.dp) //klo height gk diatur bisa error infinity
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp) // Atur jarak antara item
    ) {
        items(data, key = { item -> item.id }) { item ->
            Item(item = item, navController = navController, modifier = Modifier.fillMaxWidth())
        }
    }

    Spacer(modifier = Modifier.height(46.dp))
}


@Composable
fun Item(
    item: Batik,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Surface(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .clickable {
                navController.navigate(Screen.DetailBatik.createRoute(batikId = item.id))
            }
            .padding(10.dp)
    ) {
        Column() {
            AsyncImage(
                model = item.photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.subtitle2,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = item.desc,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "Rp. ${item.price}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = colorResource(id = R.color.orange_light)
                    )
                    Text(
                        text = item.rating.toString(),
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    val item = listDummy[0]

    MaterialTheme {
        Item(item = item, navController = rememberNavController())
    }
}



@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
//        fontSize = 26.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Gray,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}



@Composable
fun HomeBanner(name: String?) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.Gray)
            .fillMaxWidth()
            ,
        contentAlignment = Alignment.CenterStart
    ){
        Image(
            painter = painterResource(id = R.drawable.banner_home_jajar),
            contentDescription = "banner",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier.padding(start = 20.dp)
        ){
            Text(
                text = "Hello $name",
//                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.background,
            )
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryItem(title: String) {

    var isClick by remember { mutableStateOf(title == categoryDummy[0]) }

    // masih blm bisa di klik

    Chip(
        onClick = {
            isClick = !isClick
        },
        enabled = isClick,
        colors = ChipDefaults.chipColors(
            contentColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = Color.White,
            disabledContentColor = Color.Black
        ),
        border = BorderStroke(0.5.dp, Color.Gray),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
