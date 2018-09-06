package com.vsantander.speedrun.utils.factory

import com.vsantander.speedrun.domain.model.User

/**
 * Factory class for User related instances
 */
class UserFactory {

    companion object {

        fun makeUserModel(): User {
            return User(id = DataFactory.randomUuid(),
                    name = DataFactory.randomUuid())
        }

    }
}