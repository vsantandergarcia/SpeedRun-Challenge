package com.vsantander.speedrun.domain.usecases.base

import io.reactivex.Single

abstract class SingleResponseUseCase<T>
    : RxUseCase<RxUseCase.NoRequestValues, Single<T>>()