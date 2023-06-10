package com.capstone.batikin.ui.screen.payment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.capstone.batikin.ui.ui.theme.Shapes
import com.capstone.batikin.R
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.ui.components.TopBarGeneral
import com.capstone.batikin.ui.screen.map.Address


class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatikInTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopBarGeneral(
                            titleResId = R.string.title_payment
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.Top,
                            //                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            HeaderMini(text = "Alamat")
                            Address()
                            Spacer(modifier = Modifier.height(16.dp))
                            HeaderMini(text = "Jumlah barang")
                            PaymentScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderMini(text: String){
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun PaymentScreen() {
    val productList = remember { mutableStateListOf<Batik>() }
    productList.add(listDummy[0]) // Menambahkan satu item batik dari data dummy

    val productCountMap = remember { mutableStateMapOf<Int, Int>() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                val product = productList[0]
                val count = productCountMap[product.id] ?: 0
                val totalPrice = product.price * count

                PaymentItem(
                    item = product,
                    count = count,
                    totalPrice = totalPrice,
                    onProductCountChanged = { id, newCount ->
                        productCountMap[id] = newCount
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Button(
            onClick = { /* Tombol Bayar diklik */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Bayar",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
fun PaymentItem(
    item: Batik,
    count: Int,
    totalPrice: Int,
    onProductCountChanged: (id: Int, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = item.photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = item.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )

            Text(
                text = "Rp. $totalPrice", // Harga dikali dengan count
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        ProductCounter(
            orderId = item.id,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(item.id, count + 1) },
            onProductDecreased = { onProductCountChanged(item.id, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun PaymentItemPreview() {
    val item = listDummy[0]
    val count = 1
    val totalPrice = item.price * count

    PaymentItem(
        item = item,
        count = count,
        totalPrice = totalPrice,
//        onProductCountChanged = { rewardId, count -> },
        onProductCountChanged = { id, count -> /* buat count change */ },
        modifier = Modifier
    )
}


@Preview
@Composable
fun PaymentScreenPreview(){
    PaymentScreen()
}

