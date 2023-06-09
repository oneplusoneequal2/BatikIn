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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.batikin.R
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
    val response by mainViewModel.loginResponse.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isLogin) {
        if (isLogin == true) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            Toast.makeText(context, response?.message.toString(), Toast.LENGTH_SHORT).show()
        } else if (isLogin == false) {
            Toast.makeText(context, context.getString(R.string.invalid_data_message), Toast.LENGTH_SHORT).show()
        }
    }

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary

            )
            Spacer(Modifier.height(16.dp))

            EmailTextField(
                email = email,
                onEmailChange = { newValue ->
                    email = newValue
                    isEmailValid = newValue.matches(emailRegex)
                },
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )

            Spacer(Modifier.height(8.dp))

            PasswordTextField(
                password = password,
                onPasswordChange = { password = it },
                onImeAction = { focusManager.clearFocus() }
            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = {
                    mainViewModel.checkLogin(context, email, password)

                },
//                enabled = isButtonEnabled,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant // Warna latar belakang tombol
                ),
                modifier = Modifier.width(250.dp),

                ) {
                Text(text = stringResource(R.string.masuk))
            }

            if (isLoading == true) {
                CircularProgressIndicator()
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