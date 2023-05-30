package com.capstone.batikin.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capstone.batikin.ui.ui.theme.BatikInTheme

@Composable
fun NameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    onImeAction: () -> Unit

) {
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = name,
        onValueChange = {
            onNameChange(it)
            isError = it.isEmpty()
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Name")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            disabledBorderColor = MaterialTheme.colors.primary
        ),
        isError = isError,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onImeAction() }),
        textStyle = MaterialTheme.typography.body1.copy(
            color = if (isError) Color.Red else LocalContentColor.current
        )
    )
    if (isError) {
        Text(
            text = "Field ini tidak boleh kosong",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    }
}


@Composable
fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    onImeAction: () -> Unit
) {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
    var isEmailValid by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
            isEmailValid = it.matches(emailRegex)
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Email")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = MaterialTheme.colors.primary
        ),
        isError = email.isNotEmpty() && !isEmailValid,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onImeAction() }),
        textStyle = MaterialTheme.typography.body1.copy(
            color = if ((email.isEmpty() || !isEmailValid)) Color.Red else LocalContentColor.current
        )
    )
    if (email.isEmpty() && email.isNotEmpty()) {
        Text(
            text = "Field ini tidak boleh kosong",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    } else if (!isEmailValid && email.isNotEmpty()) {
        Text(
            text = "Format email tidak valid",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    }
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    onImeAction: () -> Unit

) {
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
            isError = it.isEmpty() || it.length < 8
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Password")
            }
        },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = MaterialTheme.colors.primary
        ),
        isError = isError,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onImeAction() }),
        textStyle = MaterialTheme.typography.body1.copy(
            color = if (isError) Color.Red else LocalContentColor.current
        )
    )
    if (password.isEmpty() && password.isNotEmpty()) {
        Text(
            text = "Field ini tidak boleh kosong",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    } else if (password.length < 8 && password.isNotEmpty()) {
        Text(
            text = "Password harus lebih dari 8 karakter",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    }
}

@Composable
fun ConfirmPasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    onImeAction: () -> Unit

) {
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = {
            onPasswordChange(it)
            isError = it.isEmpty()
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Confirm Password")
            }
        },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            disabledBorderColor = MaterialTheme.colors.primary
        ),
        isError = isError,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { onImeAction() }),
        textStyle = MaterialTheme.typography.body1.copy(
            color = if (isError) Color.Red else LocalContentColor.current
        )
    )
    if (password.isEmpty() && password.isNotEmpty()) {
        Text(
            text = "Field ini tidak boleh kosong",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    } else if (password.length < 8 && password.isNotEmpty()) {
        Text(
            text = "Password harus lebih dari 8 karakter",
            style = MaterialTheme.typography.caption,
            color = Color.Red
        )
    }
}



@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    BatikInTheme {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
        var isEmailValid by remember { mutableStateOf(true) }

        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ){

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

        }

    }
}
