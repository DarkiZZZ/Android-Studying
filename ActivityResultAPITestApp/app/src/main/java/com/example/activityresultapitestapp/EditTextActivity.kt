package com.example.activityresultapitestapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.example.activityresultapitestapp.databinding.ActivityEditTextBinding

class EditTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTextBinding
    private val resultIntent: Intent
        get() = Intent().apply {
            putExtra(EXTRA_OUTPUT_MESSAGE, binding.valueEditText.text.toString())
        }

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

        binding.cancelButton.setOnClickListener { onBackPressed() }
        binding.saveButton.setOnClickListener { onSavePressed() }

        binding.valueEditText.setText(intent.getStringExtra(EXTRA_INPUT_MESSAGE))
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED, resultIntent)
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onSavePressed() {
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    companion object{
        private const val EXTRA_INPUT_MESSAGE = "EXTRA_MESSAGE"
        private const val EXTRA_OUTPUT_MESSAGE = "EXTRA_MESSAGE"
    }
}