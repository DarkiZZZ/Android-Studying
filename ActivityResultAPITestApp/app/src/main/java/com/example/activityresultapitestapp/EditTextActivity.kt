package com.example.activityresultapitestapp

import android.content.Context
import android.content.Intent
import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.example.activityresultapitestapp.databinding.ActivityEditTextBinding

class EditTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTextBinding

    data class Output(
        val message: String,
        val confirmed: Boolean
    )

    class Contract : ActivityResultContract<String, Output>() {

        override fun createIntent(context: Context, input: String?): Intent =
            Intent(context, EditTextActivity::class.java).apply {
                putExtra(EXTRA_INPUT_MESSAGE, input)
            }

        override fun parseResult(resultCode: Int, intent: Intent?): Output? {
            if (intent == null) return null
            val message = intent.getStringExtra(EXTRA_OUTPUT_MESSAGE) ?: return null

            val confirmed = resultCode == RESULT_OK
            return Output(message, confirmed)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object{
        private const val EXTRA_INPUT_MESSAGE = "EXTRA_MESSAGE"
        private const val EXTRA_OUTPUT_MESSAGE = "EXTRA_MESSAGE"
    }
}