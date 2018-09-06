package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.LinkTO

/**
 * Factory class for LinkTO related instances
 */
class LinkTOFactory {

    companion object {
        fun makeLinkTOList(count: Int): List<LinkTO> {
            val linkTOList = mutableListOf<LinkTO>()
            repeat(count) {
                linkTOList.add(makeLinkTOModel())
            }
            return linkTOList
        }

        fun makeLinkTOModel(): LinkTO {
            return LinkTO(rel = DataFactory.randomUuid(),
                    uri = DataFactory.randomUuid())
        }
    }

}