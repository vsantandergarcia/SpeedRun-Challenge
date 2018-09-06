package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.VideoTO


/**
 * Factory class for VideoTO related instances
 */
class VideoTOFactory {

    companion object {

        private const val NUMBER_LINKS = 5

        fun makeVideoTOModel(): VideoTO {
            return VideoTO(links = LinkTOFactory.makeLinkTOList(NUMBER_LINKS))
        }
    }
}