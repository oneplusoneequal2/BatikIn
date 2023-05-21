package com.capstone.batikin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun Register() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    var isEmailValid by remember { mutableStateOf(true) }

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
                color = MaterialTheme.colors.primary
            )
            Spacer(Modifier.height(16.dp))

            NameTextField(name = name, onNameChange = { newName -> name = newName })

            Spacer(Modifier.height(8.dp))

            EmailTextField(email = email, onEmailChange = { newValue ->
                email = newValue
                isEmailValid = newValue.matches(emailRegex)
            })

            Spacer(Modifier.height(8.dp))

            PasswordTextField(
                password = password,
                onPasswordChange = { password = it }
            )

            Spacer(Modifier.height(8.dp))

            ConfirmPasswordTextField(
                password = confirmPassword,
                onPasswordChange = { confirmPassword = it }
            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = { /* pas tombol diteken ngapain */ },
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ),
                modifier = Modifier.width(250.dp),
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
