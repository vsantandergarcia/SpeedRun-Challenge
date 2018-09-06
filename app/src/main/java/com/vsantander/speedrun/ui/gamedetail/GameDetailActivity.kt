package com.vsantander.speedrun.ui.gamedetail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.vsantander.speedrun.R
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.domain.model.Run
import com.vsantander.speedrun.domain.model.Status
import com.vsantander.speedrun.ui.base.activity.BaseActivity
import com.vsantander.speedrun.extension.observe
import kotlinx.android.synthetic.main.activity_game_detail.*
import kotlinx.android.synthetic.main.activity_game_detail_content.*
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.PUSH)
class GameDetailActivity : BaseActivity() {

    companion object {
        const val EXTRA_GAME = "EXTRA_GAME"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GameDetailViewModel

    private lateinit var game: Game

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        intent?.extras?.getParcelable<Game>(EXTRA_GAME)?.let {
            game = it
            setUpViews()
            setUpViewModels()
        } ?: throw RuntimeException("bad initialization. not found some extras")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /* setUp methods */

    private fun setUpViews() {
        setUpToolbar()
        bindGame(game)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_white)
        supportActionBar?.title = ""
        collapsingToolbar.title = ""
    }

    private fun setUpViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GameDetailViewModel::class.java)

        viewModel.resource.observe(this) {
            it ?: return@observe
            progressBar.isVisible = it.status == Status.LOADING

            if (it.status == Status.SUCCESS) {
                bindRun(it.data!!)
            } else if (it.status == Status.FAILED) {
                Snackbar.make(toolbar, R.string.common_error, Snackbar.LENGTH_LONG)
                        .show()
            }
        }

        viewModel.loadFirstRunFromGameId(game.id)
    }

    /* owner methods */

    private fun bindGame(game: Game) {
        supportActionBar?.title = game.title
        collapsingToolbar.title = game.title

        Glide
                .with(this)
                .load(game.image)
                .into(gameImageView)

        Glide
                .with(this)
                .load(game.logo)
                .into(logoImageView)
    }

    private fun bindRun(run: Run) {
        timeValueTextView.text = run.time

        run.name?.let { name ->
            playerNameValueTextView.text = name
        }
        run.video?.let { video ->
            videoLink.apply {
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
                    startActivity(intent)
                }
                isVisible = true
            }
        }

    }

}