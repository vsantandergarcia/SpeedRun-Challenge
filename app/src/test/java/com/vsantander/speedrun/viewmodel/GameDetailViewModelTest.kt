package com.vsantander.speedrun.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.vsantander.speedrun.domain.model.Resource
import com.vsantander.speedrun.domain.model.Run
import com.vsantander.speedrun.domain.model.Status
import com.vsantander.speedrun.domain.usecases.GetFirstRun
import com.vsantander.speedrun.ui.gamedetail.GameDetailViewModel
import com.vsantander.speedrun.utils.RunFactory
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
class GameDetailViewModelTest {

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var getFirstRun: GetFirstRun

    @Mock
    lateinit var observer: Observer<Resource<Run>>

    lateinit var viewModel: GameDetailViewModel

    @Before
    fun setUp() {
        viewModel = GameDetailViewModel(getFirstRun)
    }

    @Test
    fun loadFirstRunLoadingState() {
        val run = RunFactory.makeRunModel()
        Mockito.`when`(getFirstRun.buildUseCase(Mockito.anyString()))
                .thenReturn(Single.just(run))
        viewModel.resource.observeForever(observer)

        viewModel.loadFirstRunFromGameId(Mockito.anyString())

        Mockito.verify(observer).onChanged(Resource(Status.LOADING))
    }

    @Test
    fun loadFirstRunSuccessState() {
        val run = RunFactory.makeRunModel()
        Mockito.`when`(getFirstRun.buildUseCase(Mockito.anyString()))
                .thenReturn(Single.just(run))

        viewModel.loadFirstRunFromGameId(Mockito.anyString())

        assert(viewModel.resource.value?.status == Status.SUCCESS)
    }

    @Test
    fun loadFirstRunErrorState() {
        Mockito.`when`(getFirstRun.buildUseCase(Mockito.anyString()))
                .thenReturn(Single.error { throw RuntimeException() })

        viewModel.loadFirstRunFromGameId(Mockito.anyString())

        assert(viewModel.resource.value?.status == Status.FAILED)
    }

    @Test
    fun loadFirstRunReturnsRun() {
        val run = RunFactory.makeRunModel()
        Mockito.`when`(getFirstRun.buildUseCase(Mockito.anyString()))
                .thenReturn(Single.just(run))

        viewModel.loadFirstRunFromGameId(Mockito.anyString())

        assert(viewModel.resource.value?.data == run)
    }
}