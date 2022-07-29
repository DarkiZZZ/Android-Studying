package com.example.viewmodelnavigation.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("Not implemented")
    }
}

inline fun <reified VM: ViewModel> BaseFragment.screenViewModel() = viewModels<VM>{
    ViewModelFactory()
}