package com.example.dialogfragmenttest

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import com.example.dialogfragmenttest.databinding.ActivityMainBinding
import kotlin.properties.Delegates.notNull

private const val KEY_COLOR = "keyColor"
private const val KEY_VOLUME = "keyVolume"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.WHITE

        binding.defaultAlertDialog.setOnClickListener {
            val dialogFragment = SimpleDialogFragment()
            dialogFragment.show(supportFragmentManager, SimpleDialogFragment.TAG)
        }
        updateUi()
        setupSimpleDialogFragmentListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
    }

    private fun setupSimpleDialogFragmentListener(){
        supportFragmentManager.setFragmentResultListener(
            SimpleDialogFragment.REQUEST_KEY,
            this,
        FragmentResultListener{_, result ->
            when(result.getInt(SimpleDialogFragment.KEY_RESPONSE)){
                DialogInterface.BUTTON_POSITIVE -> showToast("Positive button pressed")
                DialogInterface.BUTTON_NEGATIVE -> showToast("Negative button pressed")
                DialogInterface.BUTTON_NEUTRAL -> showToast("Ignore button pressed")
            }
        })
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUi(){
        binding.textViewCurrentVolume.text =
            "Current volume: ${volume}%"
        binding.colorView.setBackgroundColor(color)
    }
}