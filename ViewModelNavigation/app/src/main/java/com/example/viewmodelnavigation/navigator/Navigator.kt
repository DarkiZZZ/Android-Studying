package com.example.viewmodelnavigation.navigator

import androidx.annotation.StringRes
import com.example.viewmodelnavigation.screens.base.BaseScreen

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)

    fun toast(@StringRes messRes: Int)

    fun getString(@StringRes messRes: Int): String
}