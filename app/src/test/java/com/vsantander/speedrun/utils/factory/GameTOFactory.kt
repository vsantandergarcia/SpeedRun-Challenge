package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.GameTO

/**
 * Factory class for GameTO related instances
 */
class GameTOFactory {

    companion object {
        fun makeGameTOList(count: Int): List<GameTO> {
            val gameTOList = mutableListOf<GameTO>()
            repeat(count) {
                gameTOList.add(makeGameTOModel())
            }
            return gameTOList
        }

        fun makeGameTOModel(): GameTO {
            return GameTO(id = DataFactory.randomUuid(),
                    names = NameTOFactory.makeNameTOModel(),
                    assets = GameAssetsTOFactory.makeGameAssetsTOModel())
        }
    }

}