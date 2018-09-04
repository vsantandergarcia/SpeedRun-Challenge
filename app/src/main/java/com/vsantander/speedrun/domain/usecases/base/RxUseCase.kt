package com.vsantander.speedrun.domain.usecases.base

abstract class RxUseCase<in Params, out R : Any> {

    /**
     * Data passed to a request.
     */
    interface RequestValues

    /**
     * Data received from a response.
     */
    interface ResponseValues

    enum class NoRequestValues : RequestValues {
        INSTANCE
    }

    enum class NoResponseValues : ResponseValues {
        INSTANCE
    }

    abstract fun buildUseCase(params: Params? = null): R
}