package com.example.basedframemvvm

import android.app.Application
import com.example.basedframemvvm.model.colors.InMemoryColorsRepository

class App : Application() {

    val models = listOf<Any>(
        InMemoryColorsRepository()
    )
}