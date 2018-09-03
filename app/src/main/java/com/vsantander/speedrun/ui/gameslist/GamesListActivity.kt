package com.vsantander.speedrun.ui.gameslist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.vsantander.speedrun.R
import com.vsantander.speedrun.domain.model.Status
import com.vsantander.speedrun.extension.observe
import com.vsantander.speedrun.ui.base.activity.BaseActivity
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.FADE)
class GamesListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GamesListViewModel

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
        setUpViews()
        setUpViewModels()
    }

    /* setUp methods */

    private fun setUpViews() {

    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GamesListViewModel::class.java)

        viewModel.resource.observe(this) {
            it ?: return@observe

            if (it.status == Status.SUCCESS) {

            } else if (it.status == Status.FAILED) {

            }
        }

        viewModel.loadGamesList()
    }
}