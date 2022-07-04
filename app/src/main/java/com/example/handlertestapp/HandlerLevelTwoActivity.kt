package com.example.handlertestapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.forEach
import com.example.handlertestapp.databinding.ActivityHandlerLevelTwoBinding
import kotlin.random.Random

class HandlerLevelTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHandlerLevelTwoBinding
    private val handler = Handler(Looper.getMainLooper()){
        when(it.what){
            MSG_TOGGLE_BUTTON -> toggleTestButton()
            MSG_NEXT_RANDOM_COLOR -> getRandomColor()
            MSG_SHOW_TOAST -> showToast()
        }
        return@Handler true
    }
    private val token = Any()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerLevelTwoBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.root.forEach {
            if (it is Button) it.setOnClickListener(universalButtonListener)
        }
    }

    private fun toggleTestButton(){
        binding.testButton.isEnabled = !binding.testButton.isEnabled
    }

    private fun getRandomColor(){
        val randomColor = -Random.nextInt(255 * 255 * 255)
        binding.colorView.setBackgroundColor(randomColor)
    }

    private fun showToast(){
        Toast.makeText(this, "message", Toast.LENGTH_SHORT).show()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("NewApi")
    private val universalButtonListener = View.OnClickListener{
        Thread{
            when(it.id){
                R.id.toggleButton ->{
                    val message = Message()
                    message.what = MSG_TOGGLE_BUTTON
                    handler.sendMessage(message)
                }
                R.id.randomColorButton ->{
                    val message = Message()
                    message.what = MSG_NEXT_RANDOM_COLOR
                    handler.sendMessage(message)
                }
                R.id.toggleDelayedButton ->{
                    val message = Message()
                    message.what = MSG_TOGGLE_BUTTON
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.randomColorDelayedButton ->{
                    val message = Message.obtain(handler){
                        getRandomColor()
                    }
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.randomColorTokenDelayedButton ->{
                    val message = Message()
                    message.what = MSG_NEXT_RANDOM_COLOR
                    message.obj = token
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.showToastButton ->{
                    val message = Message()
                    message.what = MSG_SHOW_TOAST
                    message.obj = token
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.cancelActionButton -> handler.removeCallbacksAndMessages(token)
            }

        }.start()
    }

    companion object{
        private const val MSG_TOGGLE_BUTTON = 1
        private const val MSG_NEXT_RANDOM_COLOR = 2
        private const val MSG_SHOW_TOAST = 3
        private const val DELAY = 2000L
    }
}