package com.vsantander.speedrun.data.remote.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class GameTO(
        @SerializedName("id")
        val id: String,

        @SerializedName("names")
        val names: NameTO,

        @SerializedName("abbreviation")
        val abbreviation: String,

        @SerializedName("weblink")
        val webLink: String,

        @SerializedName("released")
        val released: Int,

        @SerializedName("ruleset")
        val ruleset: RulesetTO,

        @SerializedName("romhack")
        val romhack: Boolean,

        @SerializedName("platforms")
        val platforms: List<String>,

        @SerializedName("regions")
        val regions: List<String>,

        @SerializedName("genres")
        val genres: List<String>,

        @SerializedName("engines")
        val engines: List<String>,

        @SerializedName("developers")
        val developers: List<String>,

        @SerializedName("publishers")
        val publishers: List<String>,

        @SerializedName("moderators")
        val moderators: JsonObject,

        @SerializedName("created")
        val created: String,

        @SerializedName("release-date")
        val releaseDate: String,

        @SerializedName("assets")
        val assets: GameAssetsTO,

        @SerializedName("links")
        val links: List<LinkTO>
)