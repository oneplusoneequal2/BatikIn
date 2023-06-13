package com.capstone.batikin.ui.screen.map

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.batikin.ui.ui.theme.BatikInTheme


@Composable
fun Address() {
    var address by remember { mutableStateOf("") }
    val context = LocalContext.current

    val launcherMap =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == 200) {
                address = it.data?.getStringExtra("map_extra").toString()
            }
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier
                .weight(1f),
            label = { Text("Masukan Alamat") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            )
        )

        IconButton(
            onClick = {
                launcherMap.launch(Intent(context, MapsActivity::class.java))
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Open Maps",
                tint = MaterialTheme.colors.primary
            )
        }

    }
}

@Preview
@Composable
fun AddressPreview() {
    BatikInTheme() {
        Address()
    }
}