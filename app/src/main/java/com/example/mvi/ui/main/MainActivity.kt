package com.example.mvi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvi.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMainFragment()
    }

    fun showMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment")
            .commit()
    }
}
