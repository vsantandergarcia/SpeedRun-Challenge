package com.vsantander.speedrun.usecases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.speedrun.data.repository.GameRepositoryImpl
import com.vsantander.speedrun.domain.usecases.GetListGames
import com.vsantander.speedrun.utils.factory.GameFactory
import com.vsantander.speedrun.utils.RxImmediateSchedulerRule
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetListGamesTest {

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
    lateinit var repositoryImpl: GameRepositoryImpl

    lateinit var gameListGames: GetListGames

    @Before
    fun setUp() {
        gameListGames = GetListGames(repositoryImpl)
    }

    @Test
    fun getListGamesReturnGames() {
        val gamesList = GameFactory.makeGameList(NUMBER_GAMES)
        Mockito.`when`(repositoryImpl.getListGames())
                .thenReturn(Single.just(gamesList))

        val testObserver = gameListGames.buildUseCase(false).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult.size, CoreMatchers.`is`(NUMBER_GAMES))
        assert(listResult == gamesList)
    }

    @Test
    fun getListGamesEmptyReturnGames() {
        val gamesList = GameFactory.makeGameList(0)
        Mockito.`when`(repositoryImpl.getListGames())
                .thenReturn(Single.just(gamesList))

        val testObserver = gameListGames.buildUseCase(false).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult.size, CoreMatchers.`is`(0))
        assert(listResult == gamesList)
    }

    @Test
    fun getListGamesReturnGamesForceFromRemoteSource() {
        val gamesList = GameFactory.makeGameList(NUMBER_GAMES)
        Mockito.`when`(repositoryImpl.getListGames())
                .thenReturn(Single.just(gamesList))

        val testObserver = gameListGames.buildUseCase(true).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult.size, CoreMatchers.`is`(NUMBER_GAMES))
        assert(listResult == gamesList)
    }

    @Test
    fun getListGamesErrorReturnError() {
        Mockito.`when`(repositoryImpl.getListGames())
                .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = gameListGames.buildUseCase(false).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }
}