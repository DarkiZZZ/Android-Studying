package ru.msokolov.cleanarcexample.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.msokolov.cleanarcexample.R
import ru.msokolov.cleanarcexample.data.repository.UserRepositoryImpl
import ru.msokolov.cleanarcexample.databinding.ActivityMainBinding
import ru.msokolov.cleanarcexample.presentation.State.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val userRepository by lazy {
        UserRepositoryImpl(this)
    }

    private val mainViewModelFactory by lazy {
        MainViewModelFactory(userRepository)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.apply {
            getDataButton.setOnClickListener {
                viewModel.getName()
            }
            saveDataButton.setOnClickListener {
                viewModel.saveName()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            if (state != null) {
                when (state) {
                    is Success -> {
                        toast(getString(R.string.success_toast))
                        updateUI(null)
                    }
                    is Result -> {
                        updateUI(mapName(state))
                    }
                    is Error -> {
                        toast(getString(R.string.error_toast))
                        updateUI(null)
                    }
                }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(newText: String?) {
        binding.putDataEditText.text.clear()
        if (newText != null) {
            binding.showDataTextView.text = newText
        }
    }

    private fun mapName(state: Result): String {
        return "${state.userName.firstName} ${state.userName.lastName}"
    }
}