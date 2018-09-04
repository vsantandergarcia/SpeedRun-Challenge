package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class RunTO (
        @SerializedName("id")
        val id: String,

        @SerializedName("videos")
        val videos: VideoTO?,

        @SerializedName("comment")
        val comment: String?,

        @SerializedName("players")
        val players: List<PlayerTO>?,

        @SerializedName("times")
        val times: TimesTO

)