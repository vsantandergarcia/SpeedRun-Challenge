package com.vsantander.speedrun.ui.gamedetail

import android.arch.lifecycle.MutableLiveData
import com.vsantander.speedrun.data.repository.RunRepositoryImpl
import com.vsantander.speedrun.domain.model.Resource
import com.vsantander.speedrun.domain.model.Run
import com.vsantander.speedrun.domain.usecases.GetFirstRun
import com.vsantander.speedrun.extension.logd
import com.vsantander.speedrun.extension.loge
import com.vsantander.speedrun.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GameDetailViewModel @Inject constructor(
        private val getFirstRun: GetFirstRun
) : BaseViewModel() {

    val resource = MutableLiveData<Resource<Run>>()

    fun loadFirstRunFromGameId(gameId: String) {
        resource.value = Resource.loading()
        disposables += getFirstRun.buildUseCase(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("loadFirstRunFromGameId.onSuccess")
                            resource.value = Resource.success(it)
                        },
                        onError = {
                            loge("loadFirstRunFromGameId.onError", it)
                            resource.value = Resource.error(it)
                        }
                )
    }
}