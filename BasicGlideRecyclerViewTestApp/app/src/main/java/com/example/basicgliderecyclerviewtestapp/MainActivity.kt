package com.example.basicgliderecyclerviewtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicgliderecyclerviewtestapp.databinding.ActivityMainBinding
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val userService: UserService
        get() = (applicationContext as UserApp).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        adapter = UserAdapter()
        val layoutManager =  LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        userService.addListener { usersListener }
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun onDestroy() {
        super.onDestroy()
        userService.removeListener(usersListener)
    }
}