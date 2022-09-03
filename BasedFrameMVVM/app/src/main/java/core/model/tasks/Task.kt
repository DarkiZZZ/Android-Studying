package core.model.tasks

import core.model.FinalResult
import core.model.tasks.dispatchers.Dispatcher

typealias TaskListener<T> = (FinalResult<T>) -> Unit

class CancelledException: Exception()

interface Task<T> {

    fun await(): T

    fun cancel()

    fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>)

    //Listeners are called only in main thread
}