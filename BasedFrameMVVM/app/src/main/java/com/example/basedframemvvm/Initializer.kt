package com.example.basedframemvvm

import com.example.basedframemvvm.model.colors.InMemoryColorsRepository
import core.SingletonScopeDependencies
import core.model.coroutines.IoDispatcher
import core.model.coroutines.WorkerDispatcher
import kotlinx.coroutines.Dispatchers

object Initializer {

    fun initDependencies(){


        SingletonScopeDependencies.init {

            val ioDispatcher = IoDispatcher(Dispatchers.IO)
            val  workerDispatcher = WorkerDispatcher(Dispatchers.Default)

            return@init listOf(
                ioDispatcher,
                workerDispatcher,

                InMemoryColorsRepository(ioDispatcher)
            )
        }
    }
}