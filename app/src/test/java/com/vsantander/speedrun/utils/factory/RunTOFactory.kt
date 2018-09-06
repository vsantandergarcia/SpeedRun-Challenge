package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.RunTO

/**
 * Factory class for RunTO related instances
 */
class RunTOFactory {

    companion object {
        private const val NUMBER_PLAYERS = 5

        fun makeRunTOList(count: Int): List<RunTO> {
            val runTOList = mutableListOf<RunTO>()
            repeat(count) {
                runTOList.add(makeRunTOModel())
            }
            return runTOList
        }

        fun makeRunTOModel(): RunTO {
            return RunTO(id = DataFactory.randomUuid(),
                    videos = VideoTOFactory.makeVideoTOModel(),
                    comment = DataFactory.randomUuid(),
                    players = PlayerTOFactory.makePlayerTOList(NUMBER_PLAYERS),
                    times = TimesTOFactory.makeTimesTOModel())
        }
    }

}