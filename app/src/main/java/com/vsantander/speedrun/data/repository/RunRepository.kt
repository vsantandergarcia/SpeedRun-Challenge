package com.vsantander.speedrun.data.repository

import com.vsantander.speedrun.domain.model.Run
import io.reactivex.Single

interface RunRepository {

    /**
     * Gets the list of Run from a id of the Game
     *
     * @return Run list from gameId.
     */
    fun getListRunsFromGameId(gameId: String): Single<List<Run>>
}