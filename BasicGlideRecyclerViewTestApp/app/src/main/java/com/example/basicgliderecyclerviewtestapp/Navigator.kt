package com.example.basicgliderecyclerviewtestapp

import com.example.basicgliderecyclerviewtestapp.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun showToast(messageRes: Int)
}