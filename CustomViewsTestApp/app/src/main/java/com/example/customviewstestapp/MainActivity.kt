package com.example.customviewstestapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.customviewstestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val token = Any()
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.bottomButtons.setListener {
            when(it){
                BottomButtonAction.POSITIVE -> {
                    binding.bottomButtons.isProgressMode = true
                    handler.postDelayed({
                        binding.bottomButtons.isProgressMode = false
                        binding.bottomButtons.setApplyButtonText("Confirmation")
                    }, token, 2000L)
                }

                BottomButtonAction.NEGATIVE ->{
                    binding.bottomButtons.setCancelButtonText("Rejection")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(token)
    }
}