package com.vsantander.speedrun

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.vsantander.speedrun.ui.gamedetail.GameDetailActivity
import com.vsantander.speedrun.ui.gameslist.GamesListActivity
import com.vsantander.speedrun.utils.intentsTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GamesListActivityTest {

    @Rule
    @JvmField
    val intentsTestRule = intentsTestRule<GamesListActivity>(launchActivity = true)

    @Test
    fun recyclerViewIsShowing() {
        //Verify that recyclerview is showing on init screen
        onView(ViewMatchers.withId(R.id.recyclerView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onItemClickOpenDetail() {
        //Perform click recycler item
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        //Verify if the detail will be open
        Intents.intended(IntentMatchers.hasComponent(GameDetailActivity::class.java.name))
    }

}