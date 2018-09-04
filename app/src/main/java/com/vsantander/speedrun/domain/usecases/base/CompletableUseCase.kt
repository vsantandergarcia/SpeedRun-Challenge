package com.vsantander.speedrun.domain.usecases.base

import io.reactivex.Completable

abstract class CompletableUseCase<in T> :
        RxUseCase<T, Completable>()