package com.ludovic.vimont.leboncoinalbums.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.databinding.ActivityMainBinding
import com.ludovic.vimont.leboncoinalbums.screens.list.ListAlbumFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.main_activity_title)

        if (savedInstanceState == null) {
            val listAlbumFragment: Fragment = ListAlbumFragment.newInstance(this)
            supportFragmentManager.beginTransaction()
                .replace(binding.constraintLayoutContainer.id, listAlbumFragment)
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 1) {
            supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.fragments.last())
                .commitNow()
        } else {
            super.onBackPressed()
        }
    }
}