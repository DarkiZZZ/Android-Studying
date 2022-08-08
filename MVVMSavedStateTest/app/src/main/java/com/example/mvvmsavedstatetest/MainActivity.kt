package com.example.mvvmsavedstatetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmsavedstatetest.databinding.FragmentContainerBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: FragmentContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentContainerBinding.inflate(layoutInflater).also {
            setContentView(binding.root)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, MainFragment())
            .commit()
    }
}