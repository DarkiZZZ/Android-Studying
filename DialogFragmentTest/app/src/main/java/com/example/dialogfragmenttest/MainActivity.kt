package com.example.dialogfragmenttest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dialogfragmenttest.databinding.ActivityMainBinding
import org.jetbrains.annotations.NotNull
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
}