package com.example.basedframemvvm

import android.app.Application
import com.example.basedframemvvm.model.colors.InMemoryColorsRepository
import core.BaseApplication
import core.model.Repository

class App : Application(), BaseApplication {

    override val repositories: List<Repository> = listOf<Repository>(
        InMemoryColorsRepository()
    )
}