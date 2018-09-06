package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.PlayerTO

/**
 * Factory class for PlayerTO related instances
 */
class PlayerTOFactory {

    companion object {
        fun makePlayerTOList(count: Int): List<PlayerTO> {
            val playerTOList = mutableListOf<PlayerTO>()
            repeat(count) {
                playerTOList.add(makePlayerTOModel())
            }
            return playerTOList
        }

        fun makePlayerTOModel(): PlayerTO {
            return PlayerTO(id = DataFactory.randomUuid(),
                    rel = DataFactory.randomUuid(),
                    uri = DataFactory.randomUuid())
        }
    }
}