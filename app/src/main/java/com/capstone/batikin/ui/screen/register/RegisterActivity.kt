package com.capstone.batikin.ui.screen.register

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.batikin.ui.ConfirmPasswordTextField
import com.capstone.batikin.ui.EmailTextField
import com.capstone.batikin.ui.NameTextField
import com.capstone.batikin.ui.PasswordTextField
import com.capstone.batikin.ui.screen.login.LoginActivity
import com.capstone.batikin.ui.screen.main.MainActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.capstone.batikin.viewmodel.MainViewModel


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
//    var isButtonEnabled by remember { mutableStateOf(false) }

    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    var isEmailValid by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current


    val mainViewModel = viewModel<MainViewModel>()

    val isRegister by mainViewModel.isRegister.observeAsState()
    val response by mainViewModel.registerResponse.observeAsState()
    val isLoading by mainViewModel.isLoading.observeAsState()

    LaunchedEffect(isRegister) {
        if (isRegister == true) {
            context.startActivity(Intent(context, LoginActivity::class.java))
            Toast.makeText(context, response?.message.toString(), Toast.LENGTH_SHORT).show()
        } else if (isRegister == false) {
            Toast.makeText(context, response?.message.toString(), Toast.LENGTH_SHORT).show()
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
                "Buat Akun",
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.primary
            )
            Spacer(Modifier.height(16.dp))

            NameTextField(
                name = name,
                onNameChange = { newName -> name = newName },
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )

            Spacer(Modifier.height(8.dp))

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
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )

            Spacer(Modifier.height(8.dp))

            ConfirmPasswordTextField(
                password = confirmPassword,
                onPasswordChange = { confirmPassword = it },
                onImeAction = { focusManager.clearFocus() }

            )

            Spacer(Modifier.height(50.dp))

            Button(
                onClick = { mainViewModel.registerData(name, email, password) },
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ),
                modifier = Modifier.width(250.dp),
            ) {
                Text("Daftar")
            }

            if (isLoading == true) {
                CircularProgressIndicator()
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
