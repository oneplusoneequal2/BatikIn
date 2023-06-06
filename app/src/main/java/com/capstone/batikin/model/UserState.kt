package com.capstone.batikin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserState(
    var isLogin: Boolean? = false,
    var name: String? = null,
    var id: Int? = null,
    var token: String? = null
): Parcelable
