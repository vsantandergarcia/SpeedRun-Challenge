package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class NameTO(
        @SerializedName("international")
        val international: String,

        @SerializedName("japanese")
        val japanese: String,

        @SerializedName("twitch")
        val twitch: String
)