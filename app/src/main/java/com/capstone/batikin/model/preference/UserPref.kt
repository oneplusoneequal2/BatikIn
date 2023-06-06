package com.capstone.batikin.model.preference

import android.content.Context
import com.capstone.batikin.model.UserState

class UserPref(context: Context) {
    private val pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setState(value: UserState) {
        val editor = pref.edit()
        value.isLogin?.let { editor.putBoolean(LOGIN_EXTRA, it) }
        editor.putString(USERNAME_EXTRA, value.name)
        value.id?.let { editor.putInt(ID_EXTRA, it) }
        editor.putString(TOKEN_EXTRA, value.token)
        editor.apply()
    }

    fun getState(): UserState {
        val userState = UserState()
        userState.name = pref.getString(USERNAME_EXTRA, "")
        userState.id = pref.getInt(ID_EXTRA, -1)
        userState.token = pref.getString(TOKEN_EXTRA, "")
        return userState
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val LOGIN_EXTRA = "login_extra"
        private const val USERNAME_EXTRA = "user_name"
        private const val ID_EXTRA = "user_id"
        private const val TOKEN_EXTRA = "user_token"
    }
}