package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.utils.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Game related instances
 */
class GameFactory {

    companion object {

        fun makeGameList(count: Int): List<Game> {
            val gameList = mutableListOf<Game>()
            repeat(count) {
                gameList.add(makeGameModel())
            }
            return gameList
        }

        fun makeGameModel(): Game {
            return Game(id = randomUuid(),
                    title = randomUuid(),
                    image = randomUuid(),
                    logo = randomUuid())
        }

    }

}