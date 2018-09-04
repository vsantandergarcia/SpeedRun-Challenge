package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameAssetsTO (
        @SerializedName("logo")
        val logo: AssetTO,

        @SerializedName("cover-large")
        val coverLarge: AssetTO,

        @SerializedName("icon")
        val icon: AssetTO
)