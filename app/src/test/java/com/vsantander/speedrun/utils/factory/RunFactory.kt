package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.domain.model.Run

/**
 * Factory class for Run related instances
 */
class RunFactory {

    companion object {

        fun makeRunList(count: Int): List<Run> {
            val runList = mutableListOf<Run>()
            repeat(count) {
                runList.add(makeRunModel())
            }
            return runList
        }

        fun makeRunModel(): Run {
            return Run(id = DataFactory.randomUuid(),
                    name = null,
                    time = DataFactory.randomUuid(),
                    video = DataFactory.randomUuid(),
                    firstPlayerId = DataFactory.randomUuid())
        }

    }

}