package com.capstone.batikin.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.batikin.ui.ui.theme.BatikInTheme


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatikInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Register()
                }
            }
        }
    }
}

@Composable
fun Register(){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                "Buat Akun",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary

            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat fokus
                    unfocusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat tidak fokus
                    disabledBorderColor = MaterialTheme.colors.primary // Warna outline saat tidak aktif
                )
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat fokus
                    unfocusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat tidak fokus
                    disabledBorderColor = MaterialTheme.colors.primary // Warna outline saat tidak aktif
                )

            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat fokus
                    unfocusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat tidak fokus
                    disabledBorderColor = MaterialTheme.colors.primary // Warna outline saat tidak aktif
                )

            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat fokus
                    unfocusedBorderColor = MaterialTheme.colors.primary, // Warna outline saat tidak fokus
                    disabledBorderColor = MaterialTheme.colors.primary // Warna outline saat tidak aktif
                )

            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { /* pas tombol diteken ngapain */ },
//                enabled = isButtonEnabled,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant // Warna latar belakang tombol
                ),
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text("Daftar")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    BatikInTheme() {
        Register()
    }
}
