package com.vsantander.speedrun.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.domain.model.Resource
import com.vsantander.speedrun.domain.model.Status
import com.vsantander.speedrun.domain.usecases.GetListGames
import com.vsantander.speedrun.ui.gameslist.GamesListViewModel
import com.vsantander.speedrun.utils.GameFactory
import com.vsantander.speedrun.utils.RxImmediateSchedulerRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GamesListViewModelTest {

    companion object {
        private const val NUMBER_GAMES = 5
    }

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var gameListGames: GetListGames

    @Mock
    lateinit var observer: Observer<Resource<List<Game>>>

    lateinit var viewModel: GamesListViewModel

    @Before
    fun setUp() {
        viewModel = GamesListViewModel(gameListGames)
    }

    @Test
    fun loadGamesListLoadingState() {
        val gameList = GameFactory.makeGameList(NUMBER_GAMES)
        Mockito.`when`(gameListGames.buildUseCase(Mockito.anyBoolean()))
                .thenReturn(Single.just(gameList))
        viewModel.resource.observeForever(observer)

        viewModel.loadGamesList()

        Mockito.verify(observer).onChanged(Resource(Status.LOADING))
    }

    @Test
    fun loadGamesListSuccessState() {
        val gameList = GameFactory.makeGameList(NUMBER_GAMES)
        Mockito.`when`(gameListGames.buildUseCase(Mockito.anyBoolean()))
                .thenReturn(Single.just(gameList))

        viewModel.loadGamesList()

        assert(viewModel.resource.value?.status == Status.SUCCESS)
    }

    @Test
    fun loadGamesListErrorState() {
        Mockito.`when`(gameListGames.buildUseCase(Mockito.anyBoolean()))
                .thenReturn(Single.error { throw RuntimeException() })

        viewModel.loadGamesList()

        assert(viewModel.resource.value?.status == Status.FAILED)
    }

    @Test
    fun loadGamesListReturnsGames() {
        val gameList = GameFactory.makeGameList(NUMBER_GAMES)
        Mockito.`when`(gameListGames.buildUseCase(Mockito.anyBoolean()))
                .thenReturn(Single.just(gameList))

        viewModel.loadGamesList()

        assert(viewModel.resource.value?.data == gameList)
    }

    @Test
    fun loadGamesListForcingFromRemoteSourceReturnsGames() {
        val gameList = GameFactory.makeGameList(NUMBER_GAMES)
        Mockito.`when`(gameListGames.buildUseCase(true))
                .thenReturn(Single.just(gameList))

        viewModel.loadGamesList(true)

        assert(viewModel.resource.value?.data == gameList)
    }
}