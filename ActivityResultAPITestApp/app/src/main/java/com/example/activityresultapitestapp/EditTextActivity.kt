package com.example.activityresultapitestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activityresultapitestapp.databinding.ActivityEditTextBinding

class EditTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}