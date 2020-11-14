package com.ludovic.vimont.leboncoinalbums.screens.list

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.screens.MainActivity
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.interaction.BaristaListInteractions.scrollListToPosition
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class ListAlbumFragmentTest {
    @Rule
    @JvmField
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun testLoadMoreItemsWhileScrolling() {
        val numberOfItemsPerPage: Int = ListAlbumFragment.NUMBER_OF_ITEMS_PER_PAGE
        Thread.sleep(3_000)
        assertRecyclerViewItemCount(R.id.recycler_view_albums, numberOfItemsPerPage)
        scrollListToPosition(R.id.recycler_view_albums, numberOfItemsPerPage - 1)
        Thread.sleep(3_000)
        assertRecyclerViewItemCount(R.id.recycler_view_albums, numberOfItemsPerPage * 2)
    }
}