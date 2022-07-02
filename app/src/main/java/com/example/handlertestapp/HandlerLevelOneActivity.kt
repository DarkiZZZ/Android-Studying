package com.example.handlertestapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.forEach
import com.example.handlertestapp.databinding.ActivityHandlerLevelOneBinding
import kotlin.random.Random

class HandlerLevelOneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHandlerLevelOneBinding
    private val handler = Handler(Looper.getMainLooper())
    private val token = Any()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerLevelOneBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.root.forEach {
            if (it is Button) it.setOnClickListener(universalButtonListener)
        }
    }

    @SuppressLint("NewApi")
    private val universalButtonListener = View.OnClickListener {
        Thread{
            when(it.id){
                R.id.toggleButton ->
                    handler.post{toggleTestButton()}
                R.id.randomColorButton ->
                    handler.post { getRandomColor() }
                R.id.toggleDelayedButton ->
                    handler.postDelayed({toggleTestButton()}, DELAY)
                R.id.randomColorDelayedButton ->
                    handler.postDelayed({getRandomColor()}, DELAY)
                R.id.randomColorTokenDelayedButton ->
                    handler.postDelayed({getRandomColor()}, token, DELAY)
                R.id.showToastButton ->
                    handler.postDelayed({showToast("2 seconds left")}, token, DELAY)
                R.id.cancelActionButton ->
                    handler.removeCallbacksAndMessages(token)
            }
        }.start()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun toggleTestButton(){
        binding.testButton.isEnabled = !binding.testButton.isEnabled
    }

    private fun getRandomColor(){
        val randomColor = -Random.nextInt(255 * 255 * 255)
        binding.colorView.setBackgroundColor(randomColor)
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        @JvmStatic private val DELAY = 2000L
    }

}