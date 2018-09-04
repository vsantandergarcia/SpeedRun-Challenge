package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class TimesTO (
        @SerializedName("primary")
        val primary: String?,

        @SerializedName("realtime")
        val realtime: String?
)