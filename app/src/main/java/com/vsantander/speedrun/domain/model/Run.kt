package com.vsantander.speedrun.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Run(
        val id: String,
        var name: String?,
        val time: String,
        val video: String?,
        val firstPlayerId: String
) : Parcelable