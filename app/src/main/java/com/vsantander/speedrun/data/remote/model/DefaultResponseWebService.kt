package com.vsantander.speedrun.data.remote.model

import com.google.gson.annotations.SerializedName

class DefaultResponseWebService<T>(
        @SerializedName("data")
        var data: T
)