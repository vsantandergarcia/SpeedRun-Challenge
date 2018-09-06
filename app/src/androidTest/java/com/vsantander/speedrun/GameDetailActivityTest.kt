package com.vsantander.speedrun

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.vsantander.speedrun.domain.model.Game
import com.vsantander.speedrun.extension.fromJson
import com.vsantander.speedrun.ui.gamedetail.GameDetailActivity
import com.vsantander.speedrun.utils.activityTestRule
import okio.Okio
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.nio.charset.StandardCharsets

@RunWith(AndroidJUnit4::class)
class GameDetailActivityTest {

    @Rule
    @JvmField
    val activityTestRule = activityTestRule<GameDetailActivity>(launchActivity = false)

    @Before
    fun setUp() {
        val fileContent = getFileContentAsString("game.json")
        val game = Gson().fromJson<Game>(fileContent)

        val i = Intent()
        i.putExtra(GameDetailActivity.EXTRA_GAME, game)
        activityTestRule.launchActivity(i)
    }

    @Test
    fun infoViewIsShowing() {
        //Verify that the game info is setted in the textViews
        Espresso.onView(ViewMatchers.withId(R.id.playerNameValueTextView))
                .check(ViewAssertions.matches(not(ViewMatchers.withText(R.string.game_detail_no_info))))
        Espresso.onView(ViewMatchers.withId(R.id.timeValueTextView))
                .check(ViewAssertions.matches(not(ViewMatchers.withText(R.string.game_detail_no_info))))

        // Verify that the video logo is showing
        Espresso.onView(ViewMatchers.withId(R.id.videoLink))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun parallaxToolbarTest() {
        //Verify that appbar is expanded
        Espresso.onView(ViewMatchers.withId(R.id.appbar)).check(ViewAssertions.matches(ViewMatchers.withContentDescription(R.string.img_expanded)))

        //perform click and swipe up
        Espresso.onView(ViewMatchers.withId(R.id.appbar)).perform(ViewActions.click(), ViewActions.swipeUp())

        //We can't be really sure if the swiping has finished by the time we come to this point on all devices (slow)
        //so either a collaped or collapsing state passes the test
        Espresso.onView(ViewMatchers.withId(R.id.appbar)).check(ViewAssertions.matches(Matchers.anyOf(
                ViewMatchers.withContentDescription(R.string.img_collapsed),
                ViewMatchers.withContentDescription(R.string.img_collapsing))))
    }

    @Throws(IOException::class)
    private fun getFileContentAsString(fileName: String): String {
        val inputStream =
                javaClass.classLoader.getResourceAsStream("sampledata/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(StandardCharsets.UTF_8)
    }

}