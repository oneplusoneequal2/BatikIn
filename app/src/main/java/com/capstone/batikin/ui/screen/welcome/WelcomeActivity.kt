package com.capstone.batikin.ui.screen.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.capstone.batikin.R
import com.capstone.batikin.ui.screen.login.LoginActivity
import com.capstone.batikin.ui.screen.register.RegisterActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class WelcomeActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BatikInTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WelcomeApp()
                }
            }
        }
    }
}

@Composable
fun WelcomeApp() {
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val orangeColor = Color(0xFFFFA500)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = orangeColor,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = orangeColor,
            darkIcons = useDarkIcons
        )
    }

    Column(
        modifier = Modifier
            .background(colorResource(R.color.orange_light))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_batikin),
            contentDescription = stringResource(R.string.logo_img),
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "BatikIn",
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
            color = colorResource(R.color.black),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier
                .width(250.dp)
                .height(45.dp)
        ) {
            Text(
                text = "Login",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier
                .width(250.dp)
                .height(45.dp)
        ) {
            Text(
                text = "Sign Up",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace

            )
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BatikInTheme {
        WelcomeApp()
    }
}