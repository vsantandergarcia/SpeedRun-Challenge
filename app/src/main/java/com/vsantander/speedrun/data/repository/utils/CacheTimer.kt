package com.vsantander.speedrun.data.repository.utils

import java.util.concurrent.TimeUnit

class CacheTimer(initDirty: Boolean, time: Long, timeUnit: TimeUnit) {

    var isCacheDirty: Boolean = false
        get() = field || System.currentTimeMillis() - cacheTime > timeToExpiring

    private var cacheTime: Long = 0
    private val timeToExpiring: Long = timeUnit.toMillis(time)

    init {
        isCacheDirty = initDirty
        cacheTime = System.currentTimeMillis()
    }

    fun markValid() {
        isCacheDirty = false
        cacheTime = System.currentTimeMillis()
    }

    fun markInvalid() {
        this.isCacheDirty = true
    }
}