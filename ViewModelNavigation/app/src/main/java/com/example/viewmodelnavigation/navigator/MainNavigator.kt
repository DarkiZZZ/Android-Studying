package com.example.viewmodelnavigation.navigator

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.viewmodelnavigation.screens.base.BaseScreen

class MainNavigator(application: Application) : AndroidViewModel(application), Navigator {
    override fun launch(screen: BaseScreen) {
        TODO("Not yet implemented")
    }

    override fun goBack(result: Any?) {
        TODO("Not yet implemented")
    }

    override fun toast(messRes: Int) {
        Toast.makeText(getApplication(), messRes, Toast.LENGTH_SHORT).show()
    }

    override fun getString(messRes: Int): String {
        return getApplication<Application>().getString(messRes)
    }
}