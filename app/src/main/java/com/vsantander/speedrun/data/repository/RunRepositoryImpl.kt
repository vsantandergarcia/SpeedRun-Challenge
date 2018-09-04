package com.vsantander.speedrun.data.repository

import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.RunTOMapper
import com.vsantander.speedrun.domain.model.Run
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RunRepositoryImpl @Inject constructor(
        private val service: SpeedRunWebService,
        private val mapper: RunTOMapper
) : RunRepository {

    override fun getListRunsFromGameId(gameId: String): Single<List<Run>> {
        return service.getListRunsFromId(gameId)
                .map { mapper.toEntity(it.data) }
    }

}