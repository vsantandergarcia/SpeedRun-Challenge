package com.vsantander.speedrun.data.remote.mapper

import com.vsantander.speedrun.data.remote.model.RunTO
import com.vsantander.speedrun.domain.model.Run
import javax.inject.Inject

class RunTOMapper @Inject constructor() {

    fun toEntity(value: RunTO): Run {
        val time = value.times.realtime ?: value.times.primary ?: ""
        val video = value.videos?.links?.first()?.uri
        val firstPlayerId = value.players?.first()?.id ?: ""
        return Run(
                id = value.id,
                name = null,
                time = time,
                video = video,
                firstPlayerId = firstPlayerId)
    }

    fun toEntity(values: List<RunTO>): List<Run> = values.map { toEntity(it) }
}