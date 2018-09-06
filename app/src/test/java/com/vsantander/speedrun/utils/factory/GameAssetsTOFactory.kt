package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.GameAssetsTO

/**
 * Factory class for GameAssetsTO related instances
 */
class GameAssetsTOFactory {

    companion object {

        fun makeGameAssetsTOModel(): GameAssetsTO {
            return GameAssetsTO(logo = AssetTOFactory.makeAssetTOModel(),
                    coverLarge = AssetTOFactory.makeAssetTOModel(),
                    icon = AssetTOFactory.makeAssetTOModel())
        }
    }

}