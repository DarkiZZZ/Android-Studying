package ru.msokolov.daggerexample.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.msokolov.daggerexample.ExampleApplication
import ru.msokolov.daggerexample.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExampleViewModel::class.java]
    }
    private val viewModel2 by lazy {
        ViewModelProvider(this, viewModelFactory)[ExampleViewModel2::class.java]
    }

    private val component by lazy{
        (application as ExampleApplication).component
            .activityComponentFactory()
            .create("MY_ID", "MY_NAME_1")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.method()
        viewModel2.method()
        Log.d(TAG, viewModel.toString())
        Log.d(TAG, viewModel2.toString())

        binding.textView.setOnClickListener {
            Intent(this, MainActivity2::class.java).apply {
                startActivity(this)
            }
        }
    }

    companion object{
        private const val TAG = "ViewModelTest"
    }
}