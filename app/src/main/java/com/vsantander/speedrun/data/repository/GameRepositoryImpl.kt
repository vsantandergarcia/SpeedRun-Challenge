package com.vsantander.speedrun.data.repository

import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.GameTOMapper
import com.vsantander.speedrun.domain.model.Game
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
        private val service: SpeedRunWebService,
        private val mapper: GameTOMapper
) : GameRepository {

    override fun getListGames(): Single<List<Game>> {
        return service.getListGames()
                .map { mapper.toEntity(it.data) }
    }
}

