package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener
import com.example.basicgliderecyclerviewtestapp.tasks.EmptyResult

data class UserListItem(
    val user: User,
    val isInProgress: Boolean
)

class UserListViewModel(private val userService: UserService) : ViewModel() {

    private val viewModelUsers = MutableLiveData<List<UserListItem>>()
    var users: LiveData<List<UserListItem>> = viewModelUsers

    private val userIdsInProgress = mutableSetOf<Long>()
    private var usersResult: Result<List<User>> = EmptyResult()
        set(value) {
            field = value
    }
    private val listener: UsersListener = {
        viewModelUsers.value = it
    }

    init {
        loadUsers()
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