package com.ludovic.vimont.leboncoinalbums.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ludovic.vimont.leboncoinalbums.databinding.ActivityMainBinding
import com.ludovic.vimont.leboncoinalbums.screens.list.ListAlbumFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val listAlbumFragment: Fragment = ListAlbumFragment.newInstance(this)
            supportFragmentManager.beginTransaction()
                .replace(binding.constraintLayoutContainer.id, listAlbumFragment)
                .commitNow()
        }
    }
}