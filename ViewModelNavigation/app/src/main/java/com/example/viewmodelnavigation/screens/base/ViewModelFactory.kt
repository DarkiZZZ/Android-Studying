package com.example.viewmodelnavigation.screens.base

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.example.viewmodelnavigation.navigator.ARG_SCREEN
import com.example.viewmodelnavigation.navigator.MainNavigator
import com.example.viewmodelnavigation.navigator.Navigator

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not implemented")
    }
}


inline fun <reified VM: ViewModel> screenViewModel() = viewModels<VM>{
    ViewModelFactory()
}

