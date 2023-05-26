package com.capstone.batikin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Orderbatik(
    val batik: Batik,
    val count: Int
): Parcelable