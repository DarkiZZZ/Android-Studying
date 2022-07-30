package com.example.viewmodelnavigation.screens.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.viewmodelnavigation.Event
import com.example.viewmodelnavigation.R
import com.example.viewmodelnavigation.navigator.Navigator
import com.example.viewmodelnavigation.screens.base.BaseViewModel
import com.example.viewmodelnavigation.screens.home.HomeFragment

class EditViewModel(
    private val navigator: Navigator,
    screen: EditFragment.Screen
) : BaseViewModel(){

    private val mutableInitialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEvent: LiveData<Event<String>> = mutableInitialMessageEvent

    init {
        mutableInitialMessageEvent.value = Event(screen.initialValue)
    }

    fun onSavePressed(message: String){
        if (message.isBlank()){
            navigator.toast(R.string.empty_message)
            return
        }
        navigator.goBack(message)
    }

    fun onCancelPressed(){
        navigator.goBack()
    }
}