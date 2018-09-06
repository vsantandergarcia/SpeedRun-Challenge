package com.vsantander.speedrun.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.RunTOMapper
import com.vsantander.speedrun.data.remote.model.DefaultResponseWebService
import com.vsantander.speedrun.data.repository.RunRepositoryImpl
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.utils.Constants
import com.vsantander.speedrun.utils.RxImmediateSchedulerRule
import com.vsantander.speedrun.utils.factory.DataFactory
import com.vsantander.speedrun.utils.factory.RunTOFactory
import io.reactivex.Single
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
class RunRepositoryTest {

    companion object {
        private const val NUMBER_RUNS = 5
    }

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var service: SpeedRunWebService

    lateinit var mapper: RunTOMapper

    lateinit var repository: RunRepositoryImpl

    @Before
    fun setUp() {
        mapper = RunTOMapper()
        repository = RunRepositoryImpl(service, mapper)
        repository.cacheTimer = CacheTimer(true,
                Constants.TIMEOUT_CACHE_REPOSITORY.toLong(), TimeUnit.MINUTES)
    }

    @Test
    fun getListRunsReturnRuns() {
        val runTOList = RunTOFactory.makeRunTOList(NUMBER_RUNS)
        val runList = runTOList.map { mapper.toEntity(it) }
        Mockito.`when`(service.getListRunsFromId(Mockito.anyString()))
                .thenReturn(Single.just(DefaultResponseWebService(runTOList)))

        val gameId = DataFactory.randomUuid()
        val testObserver = repository.getListRunsFromGameId(gameId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, `is`(NUMBER_RUNS))
        assert(result == runList)
    }

    @Test
    fun getListGamesErrorReturnError() {
        Mockito.`when`(service.getListRunsFromId(Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        val gameId = DataFactory.randomUuid()
        val testObserver = repository.getListRunsFromGameId(gameId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }

    @Test
    fun getListRunsReturnRunsAfterInvalidateCache() {
        val runTOList = RunTOFactory.makeRunTOList(NUMBER_RUNS)
        val runList = runTOList.map { mapper.toEntity(it) }
        Mockito.`when`(service.getListRunsFromId(Mockito.anyString()))
                .thenReturn(Single.just(DefaultResponseWebService(runTOList)))

        val gameId = DataFactory.randomUuid()
        var testObserver = repository.getListRunsFromGameId(gameId).test()
        testObserver.awaitTerminalEvent()

        repository.invalidateCache()
        testObserver = repository.getListRunsFromGameId(gameId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, `is`(NUMBER_RUNS))
        assert(result == runList)
    }
}