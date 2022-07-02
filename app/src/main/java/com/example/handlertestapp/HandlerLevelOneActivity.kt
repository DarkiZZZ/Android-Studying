package com.example.handlertestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.handlertestapp.databinding.ActivityHandlerLevelOneBinding

class HandlerLevelOneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHandlerLevelOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerLevelOneBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}