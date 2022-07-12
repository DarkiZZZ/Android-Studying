package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener

class UserListViewModel(private val userService: UserService) : ViewModel() {

    private val viewModelUsers = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = viewModelUsers

    init {
        loadUsers()
    }
    private val listener: UsersListener = {
        viewModelUsers.value = it
    }
    fun loadUsers(){
        userService.addListener { listener }
    }

    fun relocateUser(user: User, relocation: Int){
        userService.relocateUser(user, relocation)
    }

    fun deleteUser(user: User){
        userService.deleteUser(user)
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

}