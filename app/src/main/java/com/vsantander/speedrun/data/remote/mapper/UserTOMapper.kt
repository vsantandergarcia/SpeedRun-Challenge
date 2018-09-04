package com.vsantander.speedrun.data.remote.mapper

import com.vsantander.speedrun.data.remote.model.UserTO
import com.vsantander.speedrun.domain.model.User
import javax.inject.Inject

class UserTOMapper @Inject constructor() {

    fun toEntity(value: UserTO): User {
        return User(
                id = value.id,
                name = value.names.international)
    }

    fun toEntity(values: List<UserTO>): List<User> = values.map { toEntity(it) }

}