package core.model.tasks

import core.model.FinalResult

typealias TaskListener<T> = (FinalResult<T>) -> Unit

interface Task<T> {

    fun await(): T

    fun cancel()

    fun enqueue(listener: TaskListener<T>)

    //Listeners are called only in main thread
}