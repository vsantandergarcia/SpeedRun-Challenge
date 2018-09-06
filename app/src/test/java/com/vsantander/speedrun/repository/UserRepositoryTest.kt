package com.vsantander.speedrun.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.vsantander.speedrun.data.remote.SpeedRunWebService
import com.vsantander.speedrun.data.remote.mapper.UserTOMapper
import com.vsantander.speedrun.data.remote.model.DefaultResponseWebService
import com.vsantander.speedrun.data.repository.UserRepositoryImpl
import com.vsantander.speedrun.data.repository.utils.CacheTimer
import com.vsantander.speedrun.utils.Constants
import com.vsantander.speedrun.utils.RxImmediateSchedulerRule
import com.vsantander.speedrun.utils.factory.DataFactory
import com.vsantander.speedrun.utils.factory.UserTOFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var service: SpeedRunWebService

    lateinit var mapper: UserTOMapper

    lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        mapper = UserTOMapper()
        repository = UserRepositoryImpl(service, mapper)
        repository.cacheTimer = CacheTimer(true,
                Constants.TIMEOUT_CACHE_REPOSITORY.toLong(), TimeUnit.MINUTES)
    }

    @Test
    fun getUserReturnUser() {
        val userTO = UserTOFactory.makeUserTOModel()
        val user = mapper.toEntity(userTO)
        Mockito.`when`(service.getUserFromId(Mockito.anyString()))
                .thenReturn(Single.just(DefaultResponseWebService(userTO)))

        val userId = DataFactory.randomUuid()
        val testObserver = repository.getUserFromGameId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == user)
    }

    @Test
    fun getUserErrorReturnError() {
        Mockito.`when`(service.getUserFromId(Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        val userId = DataFactory.randomUuid()
        val testObserver = repository.getUserFromGameId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }

    @Test
    fun getUserReturnUserAfterInvalidateCache() {
        val userTO = UserTOFactory.makeUserTOModel()
        val user = mapper.toEntity(userTO)
        Mockito.`when`(service.getUserFromId(Mockito.anyString()))
                .thenReturn(Single.just(DefaultResponseWebService(userTO)))

        val userId = DataFactory.randomUuid()
        var testObserver = repository.getUserFromGameId(userId).test()
        testObserver.awaitTerminalEvent()

        repository.invalidateCache()
        testObserver = repository.getUserFromGameId(userId).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == user)
    }
}