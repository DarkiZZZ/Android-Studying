package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.tasks.UserTask

open class BaseViewModel : ViewModel() {

    private val tasks = mutableListOf<UserTask<*>>()

    override fun onCleared() {
        super.onCleared()
        tasks.forEach{it.cancel()}
    }

    fun <T> UserTask<T>.autoCancel(){
        tasks.add(this)
    }
}