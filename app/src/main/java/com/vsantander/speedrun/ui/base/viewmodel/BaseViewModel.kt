package com.vsantander.speedrun.ui.base.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel()  {

    val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

}