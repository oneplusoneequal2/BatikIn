package com.capstone.batikin.ui.screen.map

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
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.batikin.ui.ui.theme.BatikInTheme

@Composable
fun Address() {
    var address by remember { mutableStateOf("") }

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