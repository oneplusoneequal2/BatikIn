package com.capstone.batikin.ui.screen.main.home

import android.app.appsearch.SearchResult.MatchInfo
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.capstone.batikin.R
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.categoryDummy
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.screen.detail.DetailActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.google.android.material.resources.MaterialAttributes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeApp(modifier: Modifier = Modifier, dataList: ArrayList<Batik>) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {

        HomeBanner()

        SectionHeader(text = "Batik Categories")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(categoryDummy){
                CategoryItem(title = it)
            }
        }
        
        SectionHeader(text = "Recommendation")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(dataList) {
                Item(
                    it
                )
            }
        }

        SectionHeader(text = "Discover More")
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = rememberLazyStaggeredGridState(),
            verticalItemSpacing = 20.dp,
            modifier = Modifier.height(300.dp)
        ) {
            items(listDummy) {
                Item(
                    item = it,
                    modifier = Modifier.width(50.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
//        fontSize = 26.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Gray,
        style = MaterialTheme.typography.h6
    )
}



@Composable
fun HomeBanner() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(color = Color.Gray)
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.CenterStart
    ){
        Image(
            painter = painterResource(id = R.drawable.home_banner),
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
                text = "Hello Test!",
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
//    Column(
//        modifier = Modifier
////            .width(100.dp)
////            .height(100.dp)
//            .background(MaterialTheme.colors.primaryVariant)
//            .clip(shape = CircleShape),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = title,
////            fontSize = 16.sp,
//            style = MaterialTheme.typography.subtitle2,
//            fontWeight = FontWeight.SemiBold,
//            color = MaterialTheme.colors.onPrimary,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis,
//        )
//    }
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
    ) {
        Text(
            text = title,
//            fontSize = 16.sp,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Item(item: Batik, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Surface(
        elevation = 8.dp
    ) {
        Column(
            modifier = modifier
                .width(100.dp)
                .clip(RoundedCornerShape(20.dp))
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
//            fontSize = 20.sp,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = item.desc,
//            fontSize = 19.sp,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Rp. ${item.price}",
                fontWeight = FontWeight.Normal,
//            fontSize = 18.sp,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(10.dp))
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

@Preview(showBackground = true)
@Composable
fun HomeBannerPreview() {
    BatikInTheme {
        HomeBanner()
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
        Item(listDummy[0], )
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