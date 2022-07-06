package com.example.customviewstestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customviewstestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        with(binding.bottomButtons){
            applyButton.text  = getString(R.string.ok)
            applyButton.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                cancelButton.visibility = View.INVISIBLE
                applyButton.visibility = View.INVISIBLE
            }
        }
    }
}