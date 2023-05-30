package com.capstone.batikin.ui.screen.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.batikin.ui.EmailTextField
import com.capstone.batikin.ui.PasswordTextField
import com.capstone.batikin.ui.screen.main.MainActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.capstone.batikin.viewmodel.MainViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatikInTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Login()
                }
            }
        }
    }
}

@Composable
fun Login(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    var isEmailValid by remember { mutableStateOf(true) }

    val mainViewModel = viewModel<MainViewModel>()

    val isLogin by mainViewModel.isLogin.observeAsState()
    val response by mainViewModel.response.observeAsState()

    val context = LocalContext.current

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                "Login",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary

            )
            Spacer(Modifier.height(16.dp))

            EmailTextField(email = email, onEmailChange = { newValue ->
                email = newValue
                isEmailValid = newValue.matches(emailRegex)
            })

            Spacer(Modifier.height(8.dp))

            PasswordTextField(
                password = password,
                onPasswordChange = { password = it }
            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = {
                    mainViewModel.checkLogin(email, password)
                    if(isLogin == true) {
                        context.startActivity(Intent(context, MainActivity::class.java))
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    } else if(isLogin == false) {
                        Toast.makeText(context, "email atau password salah!", Toast.LENGTH_SHORT).show()
                    }
                },
//                enabled = isButtonEnabled,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant // Warna latar belakang tombol
                ),
                modifier = Modifier.width(250.dp),

                ) {
                Text("Masuk")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    BatikInTheme() {
        Login()
    }
}