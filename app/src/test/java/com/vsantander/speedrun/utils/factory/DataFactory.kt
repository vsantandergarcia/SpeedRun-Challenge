package com.vsantander.speedrun.utils.factory

class DataFactory {

    companion object Factory {

        fun randomInt(): Int {
            return Math.random().toInt()
        }

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

    }

}