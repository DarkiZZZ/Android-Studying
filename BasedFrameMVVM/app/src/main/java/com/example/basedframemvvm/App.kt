package com.example.basedframemvvm

import android.app.Application
import com.example.basedframemvvm.model.colors.InMemoryColorsRepository
import core.BaseApplication

class App : Application(), BaseApplication {

    override val singletonScopeDependencies: List<Any> = listOf(
        InMemoryColorsRepository()
    )
}