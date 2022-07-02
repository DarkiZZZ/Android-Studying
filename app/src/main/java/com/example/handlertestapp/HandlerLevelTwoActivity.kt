package com.example.handlertestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.handlertestapp.databinding.ActivityHandlerLevelTwoBinding

class HandlerLevelTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHandlerLevelTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerLevelTwoBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}