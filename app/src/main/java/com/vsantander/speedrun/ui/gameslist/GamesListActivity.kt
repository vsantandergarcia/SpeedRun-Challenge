package com.vsantander.speedrun.ui.gameslist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import androidx.core.view.isVisible
import com.vsantander.speedrun.R
import com.vsantander.speedrun.domain.model.Status
import com.vsantander.speedrun.extension.logd
import com.vsantander.speedrun.extension.observe
import com.vsantander.speedrun.ui.base.activity.BaseActivity
import com.vsantander.speedrun.ui.gameslist.adapter.GamesListAdapter
import kotlinx.android.synthetic.main.activity_games_list.*
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.FADE)
class GamesListActivity : BaseActivity() {

    companion object {
        private const val LIST_SPAN_COUNT = 2
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GamesListViewModel
    private lateinit var adapter: GamesListAdapter

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)
        setUpViews()
        setUpViewModels()
    }

    /* setUp methods */

    private fun setUpViews() {
        setUpToolbar()
        adapter = GamesListAdapter().apply {
            onClickAction = {
                logd("item game click with id:${it}")
            }
        }

        swipeRefreshLayout.setOnRefreshListener { viewModel.loadGamesList() }
        recyclerView.apply {
            layoutManager = GridLayoutManager(context,
                    LIST_SPAN_COUNT)
            adapter = this@GamesListActivity.adapter
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GamesListViewModel::class.java)

        viewModel.resource.observe(this) {
            it ?: return@observe

            swipeRefreshLayout.isRefreshing = it == Status.LOADING
            progressBar.isVisible = it.status == Status.LOADING

            if (it.status == Status.SUCCESS) {
                adapter.setItems(it.data!!)
            } else if (it.status == Status.FAILED) {
                Snackbar.make(recyclerView, R.string.common_error, Snackbar.LENGTH_LONG)
                        .show()
            }
        }

        viewModel.loadGamesList()
    }
}