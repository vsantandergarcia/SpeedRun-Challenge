package com.vsantander.speedrun.di

import com.vsantander.speedrun.ui.gamedetail.GameDetailActivity
import com.vsantander.speedrun.ui.gameslist.GamesListActivity
import com.vsantander.speedrun.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(ViewModelModule::class)])
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributeGamesListActivity(): GamesListActivity

    @ContributesAndroidInjector
    internal abstract fun contributeGameDetailActivity(): GameDetailActivity

}