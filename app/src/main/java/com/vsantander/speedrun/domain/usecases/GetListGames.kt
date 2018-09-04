package com.vsantander.speedrun.domain.usecases

import com.vsantander.speedrun.data.repository.GameRepositoryImpl
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.domain.usecases.base.SingleResponseUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetListGames @Inject constructor(
        private val gameRepository: GameRepositoryImpl
) : SingleResponseUseCase<List<Game>>() {

    override fun buildUseCase(params: NoRequestValues?): Single<List<Game>> {
        return gameRepository.getListGames()
    }

}