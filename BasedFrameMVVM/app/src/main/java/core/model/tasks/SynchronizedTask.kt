package core.model.tasks

import core.model.tasks.dispatchers.Dispatcher
import java.lang.IllegalStateException

class SynchronizedTask<T>(
    private val task: Task<T>
): Task<T> {

    private var cancelled = false
    private var executed = false

    override fun await(): T {
        if (cancelled) throw CancelledException()
        if (executed) throw IllegalStateException("Task has been executed")
        return task.await()
    }

    override fun cancel() {
        if (cancelled) return
        cancelled = true
        task.cancel()
    }

    override fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>) {
        if (cancelled) return
        if (executed) throw IllegalStateException("Task has been executed")
        executed = true
        task.enqueue(dispatcher, listener)
    }

}