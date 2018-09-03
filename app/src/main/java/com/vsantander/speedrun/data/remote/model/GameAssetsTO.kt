package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameAssetsTO (
        @SerializedName("logo")
        val logo: AssetTO,

        @SerializedName("cover-tiny")
        val coverTiny: AssetTO,

        @SerializedName("cover-small")
        val coverSmall: AssetTO,

        @SerializedName("cover-medium")
        val coverMedium: AssetTO,

        @SerializedName("cover-large")
        val coverLarge: AssetTO,

        @SerializedName("icon")
        val icon: AssetTO,

        @SerializedName("trophy-1st")
        val firstTrophy: AssetTO,

        @SerializedName("trophy-2nd")
        val secondTrophy: AssetTO,

        @SerializedName("trophy-3rd")
        val thirdTrophy: AssetTO,

        @SerializedName("trophy-4th")
        val fourthTrophy: AssetTO,

        @SerializedName("background")
        val background: AssetTO,

        @SerializedName("foreground")
        val foreground: AssetTO
)