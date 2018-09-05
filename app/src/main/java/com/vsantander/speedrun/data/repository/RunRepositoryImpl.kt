package com.vsantander.speedrun.data.repository

import android.util.ArrayMap
import androidx.core.util.arrayMapOf
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.RunTOMapper
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.domain.model.Run
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RunRepositoryImpl @Inject constructor(
        private val service: SpeedRunWebService,
        private val mapper: RunTOMapper
) : RunRepository {

    @Inject
    lateinit var cacheTimer: CacheTimer
    private val cache: ArrayMap<String, List<Run>> = arrayMapOf()

    override fun getListRunsFromGameId(gameId: String): Single<List<Run>> {
        if (cacheTimer.isCacheDirty) {
            cache.clear()
        }

        return when (cacheTimer.isCacheDirty || !cache.containsKey(gameId)) {
            true ->
                service.getListRunsFromId(gameId)
                        .map { mapper.toEntity(it.data) }
                        .doOnSuccess { listRuns ->
                            cache.put(gameId, listRuns)
                            cacheTimer.markValid()
                        }
            false ->
                Single.just(cache.getValue(gameId))
        }
    }

    override fun invalidateCache() {
        cacheTimer.markInvalid()
        cache.clear()
    }

}