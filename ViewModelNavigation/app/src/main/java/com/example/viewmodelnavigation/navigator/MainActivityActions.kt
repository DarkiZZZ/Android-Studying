package com.example.viewmodelnavigation.navigator

import com.example.viewmodelnavigation.MainActivity

typealias MainActivityAction = (MainActivity) -> Unit

class MainActivityActions {

    var mainActivity: MainActivity? = null
    set(activity) {
        field = activity
        if (activity != null){
            actions.forEach { it(activity) }
            actions.clear()
        }
    }

    operator fun invoke(action: MainActivityAction){
        val activity = this.mainActivity
        if (activity == null){
            actions += action
        }
        else{
            action(activity)
        }
    }

    private val actions = mutableListOf<MainActivityAction>()

    fun clear(){
        actions.clear()
    }
}