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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.batikin.ui.ui.theme.BatikInTheme

//punya adit
//@Composable
//fun TopBar(query: String, onChange: (String) -> Unit) {
//
//    TopAppBar(
//        backgroundColor = MaterialTheme.colors.primary,
//        contentPadding = PaddingValues(16.dp),
//        contentColor = Color.White
//    ) {
//
//        SearchBar(query = query, onChange = onChange)
//
//        Spacer(modifier = Modifier.width(20.dp))
//
//        Icon(
//            imageVector = Icons.Default.ShoppingCart,
//            contentDescription = null,
//            tint = MaterialTheme.colors.onPrimary
//        )
//
//    }
//}
//
//@Composable
//fun SearchBar(query: String, onChange: (String) -> Unit) {
//    TextField(
//        value = query,
//        label = { Text("Search batik here")},
//        onValueChange = onChange ,
//        modifier = Modifier
//            .clip(shape = RoundedCornerShape(30.dp)),
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.White,
//            textColor = Color.Gray
//        ),
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = null)
//        },
//    )
//}


@Composable
fun TopBar(query: String, onChange: (String) -> Unit) {

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
            tint = Color(0xFFFFA500),
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
                text = "Search batik here",
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