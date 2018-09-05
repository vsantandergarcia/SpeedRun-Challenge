package com.vsantander.speedrun.data.repository

import android.util.ArrayMap
import androidx.core.util.arrayMapOf
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.GameTOMapper
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.domain.model.Game
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
        private val service: SpeedRunWebService,
        private val mapper: GameTOMapper
) : GameRepository {

    @Inject
    lateinit var cacheTimer: CacheTimer
    private val cache: ArrayMap<String, Game> = arrayMapOf()

    override fun getListGames(): Single<List<Game>> {
        if (cacheTimer.isCacheDirty) {
            cache.clear()
        }

        return when (cacheTimer.isCacheDirty) {
            true ->
                service.getListGames()
                        .map { mapper.toEntity(it.data) }
                        .doOnSuccess { listGames ->
                            listGames.forEach { game ->
                                cache.put(game.id, game)
                            }
                            cacheTimer.markValid()
                        }
            false ->
                Single.just(cache.values.toList())
        }
    }

    override fun invalidateCache() {
        cacheTimer.markInvalid()
        cache.clear()
    }

}

