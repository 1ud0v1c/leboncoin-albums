package com.ludovic.vimont.leboncoinalbums.screens.detail

import android.content.pm.ActivityInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.screens.MainActivity
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailAlbumFragmentTest {
    @Rule
    @JvmField
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun testInsideDetailAlbumFragmentInPortrait() {
        activityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Thread.sleep(3_000)
        clickListItem(R.id.recycler_view_albums, 0)
        assertDisplayed(R.id.image_view_album_photo)
        assertDisplayed(R.id.linear_layout_album_id)
        assertDisplayed(R.id.linear_layout_album_title)
    }
}