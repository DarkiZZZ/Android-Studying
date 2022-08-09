package com.example.basedframemvvm.views

import com.example.basedframemvvm.views.base.BaseScreen

interface Navigator {

    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)

}
