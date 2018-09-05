package com.vsantander.speedrun.domain.usecases

import com.vsantander.speedrun.data.repository.GameRepositoryImpl
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.domain.usecases.base.SingleResponseUseCase
import com.vsantander.speedrun.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetListGames @Inject constructor(
        private val gameRepository: GameRepositoryImpl
) : SingleUseCase<Boolean, List<Game>>() {

    override fun buildUseCase(params: Boolean?): Single<List<Game>> {
        if (params!!) {
            gameRepository.invalidateCache()
        }
        return gameRepository.getListGames()
    }

}