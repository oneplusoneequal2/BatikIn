package com.capstone.batikin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.batikin.R
import com.capstone.batikin.ui.ui.theme.BatikInTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatikInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailApp()
                }
            }
        }
    }
}

@Composable
fun DetailApp() {
    var isExpanded by remember { mutableStateOf(false) }
    Column() {
        Box() {
            Image(
                painter = painterResource(R.drawable.logo_batikin),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { }
            )
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Rp. 90.000",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Batik Mega Mendung Warna Biru"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Deskripsi",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.default_desc),
                maxLines = if (isExpanded) 10 else 3,
                overflow = TextOverflow.Ellipsis
            )
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
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier.width(150.dp)
                ) {
                    Text(
                        text = "Beli Langsung",
                        color = colorResource(id = R.color.orange_light),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.orange_light)),
                    modifier = Modifier.width(150.dp)
                    ) {
                    Text(
                        text = "+Keranjang",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DetailAppPreview() {
    BatikInTheme {
        DetailApp()
    }
}