package com.capstone.batikin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.batikin.R
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.google.android.material.search.SearchBar

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
        backgroundColor = Color(0xFFFFA500),
        contentPadding = PaddingValues(16.dp),
        contentColor = Color.White,
        elevation = 0.dp
    ) {
        SearchBar(query = query, onChange = onChange)

        Spacer(modifier = Modifier.width(20.dp))

        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = Color.White
        )
    }
}


@Composable
fun SearchBar(query: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onChange,
        modifier = Modifier
            .width(280.dp),
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
                contentDescription = null,
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


//@Composable
//fun TopBar(query: String, onChange: (String) -> Unit) {
//    TopAppBar(
//        backgroundColor = Color.Transparent,
//        contentPadding = PaddingValues(16.dp),
//        contentColor = Color.White,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                SearchBar(query = query, onChange = onChange)
//            }
//            Spacer(modifier = Modifier.width(10.dp))
//
//            Icon(
//                imageVector = Icons.Default.ShoppingCart,
//                contentDescription = null,
//                tint = Color(0xFFFFA500),
//                modifier = Modifier.padding(8.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun SearchBar(query: String, onChange: (String) -> Unit) {
//    OutlinedTextField(
//        value = query,
//        onValueChange = onChange,
//        modifier = Modifier.fillMaxWidth(),
//        textStyle = TextStyle(color = Color.Gray),
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            textColor = Color.Gray,
//            placeholderColor = Color.Gray.copy(alpha = 0.5f),
//            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
//        ),
//        shape = RoundedCornerShape(5.dp),
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = null,
//                tint = Color.Gray
//            )
//        },
//        placeholder = {
//            Text(
//                text = "Search batik here",
//                color = Color.Gray.copy(alpha = 0.5f)
//            )
//        }
//    )
//}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    var query by remember { mutableStateOf("") }

    BatikInTheme {
        TopBar(query) { query = it }
    }
}