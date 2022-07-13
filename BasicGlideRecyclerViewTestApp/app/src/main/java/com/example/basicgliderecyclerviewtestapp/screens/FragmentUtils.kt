package com.example.basicgliderecyclerviewtestapp.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.basicgliderecyclerviewtestapp.UserApp

class ViewModelFactory(private val userApp: UserApp) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel: Any = when(modelClass){

            UserListViewModel::class.java -> {
                UserListViewModel(userApp.usersService)
            }

            UserDetailsViewModel::class.java -> {
                UserDetailsViewModel(userApp.usersService)
            }

            else -> throw IllegalStateException("Unknown viewModel class")
        }

        return viewModel as T
    }

}

fun Fragment.factory()  = ViewModelFactory(requireContext().applicationContext as UserApp)