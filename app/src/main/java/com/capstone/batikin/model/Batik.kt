package com.capstone.batikin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Batik(
    val id: Int,
    val title: String,
    val photoUrl: String,
    val price: Int,
    val desc: String,
    val rating: Double,
    var isFavourite: Boolean = false,
//    var count: Int ///
)
