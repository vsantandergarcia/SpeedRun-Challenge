package com.vsantander.speedrun.usecases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.speedrun.data.repository.RunRepositoryImpl
import com.vsantander.speedrun.data.repository.UserRepositoryImpl
import com.vsantander.speedrun.domain.usecases.GetFirstRun
import com.vsantander.speedrun.utils.factory.RunFactory
import com.vsantander.speedrun.utils.RxImmediateSchedulerRule
import com.vsantander.speedrun.utils.factory.DataFactory
import com.vsantander.speedrun.utils.factory.UserFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetFirstRunTest {

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
    lateinit var runRepository: RunRepositoryImpl

    @Mock
    lateinit var userRepository: UserRepositoryImpl

    lateinit var getFirstRun: GetFirstRun

    @Before
    fun setUp() {
        getFirstRun = GetFirstRun(runRepository, userRepository)
    }

    @Test
    fun getFirstRunReturnsRun() {
        val runList = RunFactory.makeRunList(NUMBER_RUNS)
        val firstRunId = runList.first().id
        Mockito.`when`(runRepository.getListRunsFromGameId(Mockito.anyString()))
                .thenReturn(Single.just(runList))
        val user = UserFactory.makeUserModel()
        Mockito.`when`(userRepository.getUserFromGameId(Mockito.anyString()))
                .thenReturn(Single.just(user))

        val testObserver = getFirstRun.buildUseCase(firstRunId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == runList.first())
        assert(result.name == user.name)
    }

    @Test
    fun getFirstRunWithEmptyUserReturnsRun() {
        val runList = RunFactory.makeRunList(NUMBER_RUNS)
        val firstRunId = runList.first().id
        Mockito.`when`(runRepository.getListRunsFromGameId(Mockito.anyString()))
                .thenReturn(Single.just(runList))
        Mockito.`when`(userRepository.getUserFromGameId(Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = getFirstRun.buildUseCase(firstRunId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == runList.first())
    }

    @Test
    fun getFirstRunErrorReturnError() {
        Mockito.`when`(runRepository.getListRunsFromGameId(Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        val firstRunId = DataFactory.randomUuid()
        val testObserver = getFirstRun.buildUseCase(firstRunId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }

}