package com.vsantander.speedrun.ui.gameslist

import android.arch.lifecycle.MutableLiveData
import com.vsantander.speedrun.data.repository.GameRepository
import com.vsantander.speedrun.data.repository.GameRepositoryImpl
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.domain.model.Resource
import com.vsantander.speedrun.extension.logd
import com.vsantander.speedrun.extension.loge
import com.vsantander.speedrun.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GamesListViewModel @Inject constructor(
        private val repository: GameRepositoryImpl
) : BaseViewModel() {

    val resource = MutableLiveData<Resource<List<Game>>>()

    fun loadGamesList() {
        resource.value = Resource.loading()
        disposables += repository.getListGames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("loadGamesList.onSuccess")
                            resource.value = Resource.success(it)
                        },
                        onError = {
                            loge("loadGamesList.onError", it)
                            resource.value = Resource.error(it)
                        }
                )
    }

}