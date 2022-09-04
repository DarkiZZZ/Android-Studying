package com.example.basedframemvvm

import android.app.Application
import com.example.basedframemvvm.model.colors.InMemoryColorsRepository
import core.BaseApplication
import core.model.tasks.ThreadUtils
import core.model.tasks.dispatchers.MainThreadDispatcher
import core.model.tasks.factories.SimpleTasksFactory

class App : Application(), BaseApplication {

    private val tasksFactory = SimpleTasksFactory()
    private val threadUtils = ThreadUtils.Default()
    private val dispatcher = MainThreadDispatcher()

    override val singletonScopeDependencies: List<Any> = listOf(
        tasksFactory,
        dispatcher,
        InMemoryColorsRepository(tasksFactory, threadUtils)
    )
}