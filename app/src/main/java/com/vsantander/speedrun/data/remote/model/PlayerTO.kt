package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class PlayerTO (
        @SerializedName("id")
        val id: String,

        @SerializedName("rel")
        val rel: String,

        @SerializedName("uri")
        val uri: String
)