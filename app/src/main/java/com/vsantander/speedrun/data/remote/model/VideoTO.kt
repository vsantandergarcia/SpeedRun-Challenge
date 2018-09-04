package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

data class VideoTO (
        @SerializedName("links")
        val links: List<LinkTO>?
)