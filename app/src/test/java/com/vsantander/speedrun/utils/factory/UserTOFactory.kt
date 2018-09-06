package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.data.remote.model.UserTO

/**
 * Factory class for UserTO related instances
 */
class UserTOFactory {

    companion object {

        fun makeUserTOModel(): UserTO {
            return UserTO(id = DataFactory.randomUuid(),
                    names = NameTOFactory.makeNameTOModel())
        }

    }

}