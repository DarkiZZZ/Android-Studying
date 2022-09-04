package com.example.basedframemvvm

import android.app.Application
import com.example.basedframemvvm.model.colors.InMemoryColorsRepository
import core.BaseApplication
import core.model.tasks.ThreadUtils
import core.model.tasks.dispatchers.MainThreadDispatcher
import core.model.tasks.factories.ExecutorServiceTasksFactory
import core.model.tasks.factories.HandlerThreadTasksFactory
import core.model.tasks.factories.ThreadTasksFactory
import java.util.concurrent.Executors

class App : Application(), BaseApplication {

    /**Testing Tasks factories:
    private val tasksFactory = ThreadTasksFactory()
    private val singleThreadPoolExecutorTasksFactory = ExecutorServiceTasksFactory(Executors.newSingleThreadExecutor())**/
    private val cachedThreadPoolExecutorTasksFactory = ExecutorServiceTasksFactory(Executors.newCachedThreadPool())
    private val handlerThreadTasksFactory = HandlerThreadTasksFactory()

    private val threadUtils = ThreadUtils.Default()
    private val dispatcher = MainThreadDispatcher()


    override val singletonScopeDependencies: List<Any> = listOf(
        cachedThreadPoolExecutorTasksFactory,
        dispatcher,
        InMemoryColorsRepository(handlerThreadTasksFactory, threadUtils)
    )
}