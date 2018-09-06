package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.AssetTO

/**
 * Factory class for AssetTO related instances
 */
class AssetTOFactory {

    companion object {

        fun makeAssetTOModel(): AssetTO {
            return AssetTO(uri = DataFactory.randomUuid(),
                    width = DataFactory.randomInt(),
                    height = DataFactory.randomInt())
        }
    }
}