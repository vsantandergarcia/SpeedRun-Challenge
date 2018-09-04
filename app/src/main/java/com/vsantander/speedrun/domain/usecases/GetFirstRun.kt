package com.vsantander.speedrun.domain.usecases

import com.vsantander.speedrun.data.repository.RunRepositoryImpl
import com.vsantander.speedrun.data.repository.UserRepositoryImpl
import com.vsantander.speedrun.domain.model.Run
import com.vsantander.speedrun.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetFirstRun @Inject constructor(
        private val runRepository: RunRepositoryImpl,
        private val userRepository: UserRepositoryImpl
) : SingleUseCase<String, Run>() {

    override fun buildUseCase(params: String?): Single<Run> {
        return runRepository.getListRunsFromGameId(params!!)
                .filter { !it.isEmpty() }
                .map { it[0] }
                .flatMapSingle { run ->
                    userRepository.getUserFromGameId(run.firstPlayerId)
                            .map { userTO ->
                                run.name = userTO.name
                                run
                            }
                            .onErrorReturn { run }
                }
    }
}