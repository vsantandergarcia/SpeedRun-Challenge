package com.vsantander.speedrun.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.GameTOMapper
import com.vsantander.speedrun.data.remote.model.DefaultResponseWebService
import com.vsantander.speedrun.data.repository.GameRepositoryImpl
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.utils.Constants
import com.vsantander.speedrun.utils.RxImmediateSchedulerRule
import com.vsantander.speedrun.utils.factory.GameTOFactory
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class GameRepositoryTest {

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
    lateinit var service: SpeedRunWebService

    lateinit var mapper: GameTOMapper

    lateinit var repository: GameRepositoryImpl

    @Before
    fun setUp() {
        mapper = GameTOMapper()
        repository = GameRepositoryImpl(service, mapper)
        repository.cacheTimer = CacheTimer(true,
                Constants.TIMEOUT_CACHE_REPOSITORY.toLong(), TimeUnit.MINUTES)
    }

    @Test
    fun getListGamesReturnGames() {
        val gameTOList = GameTOFactory.makeGameTOList(NUMBER_GAMES)
        val gameList = gameTOList.map { mapper.toEntity(it) }
        Mockito.`when`(service.getListGames())
                .thenReturn(Single.just(DefaultResponseWebService(gameTOList)))

        val testObserver = repository.getListGames().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, `is`(NUMBER_GAMES))
        assert(result == gameList)
    }

    @Test
    fun getListGamesErrorReturnError() {
        Mockito.`when`(service.getListGames())
                .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = repository.getListGames().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }

    @Test
    fun getListGamesReturnGamesAfterInvalidateCache() {
        val gameTOList = GameTOFactory.makeGameTOList(NUMBER_GAMES)
        val gameList = gameTOList.map { mapper.toEntity(it) }
        Mockito.`when`(service.getListGames())
                .thenReturn(Single.just(DefaultResponseWebService(gameTOList)))

        var testObserver = repository.getListGames().test()
        testObserver.awaitTerminalEvent()

        repository.invalidateCache()
        testObserver = repository.getListGames().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, `is`(NUMBER_GAMES))
        assert(result == gameList)
    }
}