package com.vsantander.speedrun.utils

class DataFactory {

    companion object Factory {

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

    }

}