package com.vsantander.speedrun.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
        val id: String,
        val title: String,
        val image: String
) : Parcelable