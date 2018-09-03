package com.vsantander.speedrun.data.repository

import com.vsantander.speedrun.domain.model.Game
import io.reactivex.Single

interface GameRepository {

    /**
     * Gets a list of Games
     *
     * @return a List of the Games available.
     */
    fun getListGames(): Single<List<Game>>
}