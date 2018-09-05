package com.vsantander.speedrun.data.repository

import android.util.ArrayMap
import androidx.core.util.arrayMapOf
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.UserTOMapper
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.domain.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val service: SpeedRunWebService,
        private val mapper: UserTOMapper
) : UserRepository {

    @Inject
    lateinit var cacheTimer: CacheTimer
    private val cache: ArrayMap<String, User> = arrayMapOf()

    override fun getUserFromGameId(gameId: String): Single<User> {
        if (cacheTimer.isCacheDirty) {
            cache.clear()
        }

        return when (cacheTimer.isCacheDirty || !cache.containsKey(gameId)) {
            true ->
                service.getUserFromId(gameId)
                        .map { mapper.toEntity(it.data) }
                        .doOnSuccess { user ->
                            cache.put(gameId, user)
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