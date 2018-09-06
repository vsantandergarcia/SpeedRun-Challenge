package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.NameTO

/**
 * Factory class for NameTO related instances
 */
class NameTOFactory {

    companion object {
        fun makeNameTOList(count: Int): List<NameTO> {
            val nameTOList = mutableListOf<NameTO>()
            repeat(count) {
                nameTOList.add(makeNameTOModel())
            }
            return nameTOList
        }

        fun makeNameTOModel(): NameTO {
            return NameTO(international = DataFactory.randomUuid(),
                    japanese = DataFactory.randomUuid(),
                    twitch = DataFactory.randomUuid())
        }
    }
}