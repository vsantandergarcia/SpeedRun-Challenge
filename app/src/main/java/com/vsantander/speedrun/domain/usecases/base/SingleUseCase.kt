package com.vsantander.speedrun.domain.usecases.base

import io.reactivex.Single

abstract class SingleUseCase<in Params, T>
    : RxUseCase<Params, Single<T>>()