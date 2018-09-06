package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.TimesTO

/**
 * Factory class for TimesTO related instances
 */
class TimesTOFactory {

    companion object {

        fun makeTimesTOModel(): TimesTO {
            return TimesTO(primary = DataFactory.randomUuid(),
                    realtime = DataFactory.randomUuid())
        }

    }

}