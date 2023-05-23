package com.capstone.batikin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Batik(
    val id: Int,
    val title: String,
    val photoUrl: String,
    val desc: String
): Parcelable
