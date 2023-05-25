package com.capstone.batikin.ui.screen.main.home

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.capstone.batikin.R
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.categoryDummy
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.screen.detail.DetailActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeApp(modifier: Modifier = Modifier, dataList: ArrayList<Batik>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color.Gray)
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                text = "Hello Test!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }

        Text(
            text = "Recommendation",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Gray
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(dataList) {
                Item(
                    it,
                    orientation = "row"
                )
            }
        }

        Text(
            text = "Categories",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Gray
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier.height(300.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categoryDummy){
                CategoryItem(title = it)
            }
        }

        Text(
            text = "Discover More",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Gray
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = rememberLazyStaggeredGridState(),
            verticalItemSpacing = 20.dp,
            modifier = Modifier.height(500.dp)
        ) {
            items(listDummy) {
                Item(
                    item = it,
                    orientation = "col"
                )
            }
        }

        Text(
            text = "Test"
        )
    }
}


@Composable
fun CategoryItem(title: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .background(colorResource(id = R.color.orange_light))
            .clip(RoundedCornerShape(20.dp))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun Item(item: Batik, orientation: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .width(if (orientation == "row") 200.dp else 50.dp)
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .clickable(true, onClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.apply {
                    putExtra("id_extra", item.id)
                    putExtra("photo_extra", item.photoUrl)
                    putExtra("title_extra", item.title)
                    putExtra("price_extra", item.price)
                    putExtra("desc_extra", item.desc)
                    putExtra("favourite_extra", item.isFavourite)
                }
                context.startActivity(intent)
            })
    ) {
        AsyncImage(
            model = item.photoUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = item.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = item.desc,
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Rp. ${item.price}",
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
            Text(
                text = item.rating.toString(),
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    BatikInTheme {
        CategoryItem(title = categoryDummy[0])
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    BatikInTheme {
        Item(listDummy[0], "row")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeAppPreview() {
    BatikInTheme {
        HomeApp(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            , listDummy)
    }
}