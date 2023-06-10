package com.capstone.batikin.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.batikin.R
import com.capstone.batikin.ui.ui.theme.BatikInTheme

@Composable
fun TopBar(query: String, onChange: (String) -> Unit) {

    val orangeColor = Color(0xFFFFA500)

    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(8.dp),
        contentColor = Color.White,
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp)
        ) {
            SearchBar(query = query, onChange = onChange)
        }

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = orangeColor,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}


@Composable
fun SearchBar(query: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.Gray),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Gray,
            placeholderColor = Color.Gray.copy(alpha = 0.5f),
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
            backgroundColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = Color.Gray
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_batik_here),
                color = Color.Gray.copy(alpha = 0.5f)
            )
        }
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