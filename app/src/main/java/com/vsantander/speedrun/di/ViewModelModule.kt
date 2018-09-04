package com.vsantander.speedrun.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.vsantander.speedrun.ui.base.viewmodel.ViewModelFactory
import com.vsantander.speedrun.ui.gamedetail.GameDetailViewModel
import com.vsantander.speedrun.ui.gameslist.GamesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GamesListViewModel::class)
    abstract fun bindGamesListViewModel(viewModel: GamesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameDetailViewModel::class)
    abstract fun bindGameDetailViewModel(viewModel: GameDetailViewModel): ViewModel
}