package com.example.viewmodelnavigation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.viewmodelnavigation.R
import com.example.viewmodelnavigation.navigator.Navigator
import com.example.viewmodelnavigation.screens.base.BaseViewModel
import com.example.viewmodelnavigation.screens.edit.EditFragment

class HomeViewModel(
    private val navigator: Navigator,
    screen: HomeFragment.Screen
) : BaseViewModel() {

    private val mutableCurrentMessageLiveData = MutableLiveData<String>()
    val currentMessageLiveData: LiveData<String> = mutableCurrentMessageLiveData

    init {
        mutableCurrentMessageLiveData.value = navigator.getString(R.string.hello_world)
    }

    override fun onResult(result: Any) {
        if (result is String){
            mutableCurrentMessageLiveData.value = result
        }
    }

    fun onEditPressed(){
        navigator.launch(EditFragment.Screen(initialValue = currentMessageLiveData.value!!))
    }
}