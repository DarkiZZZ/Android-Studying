package com.example.activityresultapitestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.activityresultapitestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        Log.d(MainActivity::class.java.simpleName, "Permission granted: $it")
    }

    private val editMessageLauncher = registerForActivityResult(EditTextActivity.Contract()){
        Log.d(MainActivity::class.java.simpleName, "Edit result: $it")
        if (it.confirmed){
            binding.testTextView.text = it.message
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.requestPermissionButton.setOnClickListener{ requestPermission()}
        binding.editTextButton.setOnClickListener { editMessage() }
    }

    private fun editMessage() {
        editMessageLauncher.launch(binding.testTextView.text.toString())
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}