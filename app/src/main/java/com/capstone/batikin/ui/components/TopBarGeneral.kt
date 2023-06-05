package com.capstone.batikin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    titleResId: Int,
    modifier: Modifier = Modifier
) {
    val title = stringResource(id = titleResId)

    TopAppBar(
        backgroundColor = Color.White,
//        contentPadding = PaddingValues(1.dp),
        contentColor = Color.White,
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFFFFA500),
                fontFamily = FontFamily.Monospace
//            modifier = Modifier.background(Color(0xFFFFA500))
            )
        }
    }
}
