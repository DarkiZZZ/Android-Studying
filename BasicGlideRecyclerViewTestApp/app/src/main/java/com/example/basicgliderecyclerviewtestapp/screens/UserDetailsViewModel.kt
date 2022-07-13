package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.UserNotFoundException
import com.example.basicgliderecyclerviewtestapp.model.UserDetails
import com.example.basicgliderecyclerviewtestapp.model.UserService

class UserDetailsViewModel(private val userService: UserService) : ViewModel() {

    private val viewModelUserDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = viewModelUserDetails

    fun loadUsers(userId: Long){
        if(viewModelUserDetails.value != null) return
        try {
            viewModelUserDetails.value = userService.getUserDetailsById(userId)
        }
        catch (e: UserNotFoundException){
            e.stackTrace
        }

    }

    fun deleteUser(){
        val userDetails: UserDetails = this.userDetails.value ?: return
        userService.deleteUser(userDetails.user)
    }
}