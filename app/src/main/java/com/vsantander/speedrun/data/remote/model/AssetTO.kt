package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class AssetTO(
        @SerializedName("uri")
        val uri: String,

        @SerializedName("width")
        val width: Int,

        @SerializedName("height")
        val height: Int
)