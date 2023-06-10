package com.capstone.batikin.ui.screen.main.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.capstone.batikin.R
import com.capstone.batikin.ui.components.TopBarGeneral

@Composable
fun HistoryScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarGeneral(
            titleResId = R.string.title_history
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Still Under Development",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
