package com.vsantander.speedrun.ui.splash

import android.os.Bundle
import android.os.Handler
import com.vsantander.speedrun.R
import com.vsantander.speedrun.ui.base.activity.BaseActivity
import com.vsantander.speedrun.ui.gameslist.GamesListActivity
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

@BaseActivity.Animation(BaseActivity.FADE)
class SplashActivity : BaseActivity() {

    companion object {
        private val SPLASH_DELAY = TimeUnit.SECONDS.toMillis(2) // 2 seconds
    }

    private val handler = Handler()

    private val runnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity<GamesListActivity>()
            finish()
        }
    }

    /* Activity methods */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(runnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}