package com.example.basicgliderecyclerviewtestapp

import android.app.Application
import com.example.basicgliderecyclerviewtestapp.model.UserService

class UserApp : Application(){
    val usersService: UserService = UserService()
}