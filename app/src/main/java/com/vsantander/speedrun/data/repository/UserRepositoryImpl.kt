package com.vsantander.speedrun.data.repository

import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.UserTOMapper
import com.vsantander.speedrun.domain.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val service: SpeedRunWebService,
        private val mapper: UserTOMapper
) : UserRepository {

    override fun getUserFromGameId(gameId: String): Single<User> {
        return service.getUserFromId(gameId)
                .map { mapper.toEntity(it.data) }
    }

}