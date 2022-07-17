package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.tasks.UserTask

class Event<T>(
    private val value: T
){

    private var handled: Boolean = false

    fun getValue(): T? {
        if (handled) return null
        handled = true
        return value
    }
}

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