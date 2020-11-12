package com.ludovic.vimont.leboncoinalbums.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ludovic.vimont.leboncoinalbums.R
import com.ludovic.vimont.leboncoinalbums.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.main_activity_title)
    }
}