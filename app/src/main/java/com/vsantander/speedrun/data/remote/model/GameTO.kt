package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameTO(
        @SerializedName("id")
        val id: String,

        @SerializedName("names")
        val names: NameTO,

        @SerializedName("assets")
        val assets: GameAssetsTO
)