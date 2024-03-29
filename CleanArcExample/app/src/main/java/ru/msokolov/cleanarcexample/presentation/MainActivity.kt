package ru.msokolov.cleanarcexample.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import ru.msokolov.cleanarcexample.R
import ru.msokolov.cleanarcexample.data.repository.UserRepositoryImpl
import ru.msokolov.cleanarcexample.data.storage.sharedPrefs.SharedPrefUserStorageImpl
import ru.msokolov.cleanarcexample.databinding.ActivityMainBinding
import ru.msokolov.cleanarcexample.domain.models.SaveUserNameParam
import ru.msokolov.cleanarcexample.domain.models.UserName
import ru.msokolov.cleanarcexample.presentation.State.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.apply {
            getDataButton.setOnClickListener {
                viewModel.getName()
            }
            saveDataButton.setOnClickListener {
                val name = SaveUserNameParam(
                    name = binding.putDataEditText.text.toString()
                )
                viewModel.saveName(name)
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
                        updateUI(mapName(state.userName))
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

    private fun mapName(userName: UserName): String {
        return "${userName.firstName} ${userName.lastName}"
    }
}