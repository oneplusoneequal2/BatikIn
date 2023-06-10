package com.capstone.batikin.ui.screen.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.capstone.batikin.model.UserState
import com.capstone.batikin.model.preference.UserPref
import com.capstone.batikin.ui.screen.BatikApp
import com.capstone.batikin.ui.screen.welcome.WelcomeActivity
import com.capstone.batikin.ui.ui.theme.BatikInTheme

class MainActivity : ComponentActivity() {

    private lateinit var userState: UserState
    private lateinit var userPref: UserPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPref = UserPref(this)
        userState = userPref.getState()
        if (userState.id == -1) {
            val intent = Intent(this, WelcomeActivity::class.java)
            finish()
            startActivity(intent)
        }

        val name = userState.name
        val id = userState.id
        val token = userState.token

        setContent {
            BatikInTheme {
                val navController = rememberNavController()
                BatikApp(navController = navController, userState = userState)
            }
        }
    }
}