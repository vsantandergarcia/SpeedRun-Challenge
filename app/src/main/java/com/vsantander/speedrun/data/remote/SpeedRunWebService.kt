package com.vsantander.speedrun.data.remote

import com.vsantander.speedrun.data.remote.model.DefaultResponseWebService
import com.vsantander.speedrun.data.remote.model.GameTO
import com.vsantander.speedrun.data.remote.model.RunTO
import com.vsantander.speedrun.data.remote.model.UserTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpeedRunWebService {

    @GET("v1/games")
    fun getListGames(): Single<DefaultResponseWebService<List<GameTO>>>

    @GET("v1/runs")
    fun getListRunsFromId(@Query("game") gameId: String): Single<DefaultResponseWebService<List<RunTO>>>

    @GET("v1/users/{userId}")
    fun getUserFromId(@Path("userId") userId: String): Single<DefaultResponseWebService<UserTO>>
}