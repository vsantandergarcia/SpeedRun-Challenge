package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class RulesetTO (
        @SerializedName("show-milliseconds")
        val showMiliseconds: Boolean,

        @SerializedName("require-verification")
        val requireVerification: Boolean,

        @SerializedName("require-video")
        val requireVideo: Boolean,

        @SerializedName("run-times")
        val runTimes: List<String>,

        @SerializedName("default-time")
        val defaultTime: String,

        @SerializedName("emulators-allowed")
        val emulatorsAllowed: Boolean
)