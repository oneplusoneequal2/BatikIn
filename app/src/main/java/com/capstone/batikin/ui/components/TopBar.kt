package com.capstone.batikin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.batikin.ui.ui.theme.BatikInTheme

@Composable
fun TopBar(query: String, onChange: (String) -> Unit) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentPadding = PaddingValues(16.dp),
        contentColor = Color.White
    ) {

        SearchBar(query = query, onChange = onChange)

        Spacer(modifier = Modifier.width(50.dp))

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary
        )

    }
}

@Composable
fun SearchBar(query: String, onChange: (String) -> Unit) {
    TextField(
        value = query,
        label = { Text("Search batik here")},
        onValueChange = onChange ,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            textColor = Color.Gray
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null)
        },
    )

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    var query by remember { mutableStateOf("") }

    BatikInTheme {
        TopBar(query) { query = it }
    }
}