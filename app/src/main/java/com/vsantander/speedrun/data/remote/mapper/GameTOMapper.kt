package com.vsantander.speedrun.data.remote.mapper

import com.vsantander.speedrun.data.remote.model.GameTO
import com.vsantander.speedrun.domain.model.Game
import javax.inject.Inject

class GameTOMapper @Inject constructor() {

    fun toEntity(value: GameTO): Game {
        return Game(
                id = value.id,
                title = value.names.international,
                image = value.assets.coverLarge.uri,
                logo = value.assets.logo.uri)
    }

    fun toEntity(values: List<GameTO>): List<Game> = values.map { toEntity(it) }

}