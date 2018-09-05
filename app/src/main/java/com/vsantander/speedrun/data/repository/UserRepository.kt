package com.vsantander.speedrun.data.repository

import com.vsantander.speedrun.domain.model.User
import io.reactivex.Single

interface UserRepository {

    /**
     * Gets the User from an id of the Game
     *
     * @return User from gameId.
     */
    fun getUserFromGameId(gameId: String): Single<User>

    /**
     * Mark cache as invalid
     */
    fun invalidateCache()
}