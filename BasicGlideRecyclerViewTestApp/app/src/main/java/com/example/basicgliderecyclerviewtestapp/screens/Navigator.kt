package com.example.basicgliderecyclerviewtestapp.screens

import com.example.basicgliderecyclerviewtestapp.model.User

interface Navigator {

    fun showDetails(user: User)

    fun goBack()

    fun showToast(messageRes: Int)
}