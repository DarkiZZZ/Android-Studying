package com.example.handlertestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.handlertestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.buttonLevelOne.setOnClickListener { changeToLevelOneHandler() }
        binding.buttonLevelTwo.setOnClickListener { changeToLevelTwoHandler() }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun changeToLevelOneHandler(){
        val intent = Intent(this, HandlerLevelOneActivity::class.java)
        startActivity(intent)
    }

    private fun changeToLevelTwoHandler(){
        val intent = Intent(this, HandlerLevelTwoActivity::class.java)
        startActivity(intent)
    }
}