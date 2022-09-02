package com.example.basedframemvvm

import android.app.Application
import com.example.basedframemvvm.model.colors.InMemoryColorsRepository
import core.BaseApplication
import core.model.Repository
import core.model.tasks.SimpleTasksFactory

class App : Application(), BaseApplication {

    private val tasksFactory = SimpleTasksFactory()

    override val repositories: List<Repository> = listOf(
        tasksFactory,
        InMemoryColorsRepository(tasksFactory)
    )
}