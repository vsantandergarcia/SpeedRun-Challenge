package com.vsantander.speedrun.data.remote

import com.vsantander.speedrun.data.remote.model.DefaultResponseWebService
import com.vsantander.speedrun.data.remote.model.GameTO
import io.reactivex.Single
import retrofit2.http.GET

interface SpeedRunWebService {

    @GET("v1/games")
    fun getListGames(): Single<DefaultResponseWebService<List<GameTO>>>

}