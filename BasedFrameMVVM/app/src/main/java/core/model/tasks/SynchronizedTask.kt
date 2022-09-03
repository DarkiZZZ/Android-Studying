package core.model.tasks

import core.model.tasks.dispatchers.Dispatcher
import java.lang.IllegalStateException
import java.util.concurrent.atomic.AtomicBoolean

class SynchronizedTask<T>(
    private val task: Task<T>
): Task<T> {

    @Volatile
    private var cancelled = false

    private var executed = false
    private var listenerCalled = AtomicBoolean(false)

    override fun await(): T {
        synchronized(this){
            if (cancelled) throw CancelledException()
            if (executed) throw IllegalStateException("Task has been executed")
            executed = true
        }
        return task.await()
    }

    override fun cancel() = synchronized(this) {
        if (listenerCalled.compareAndSet(false, true)){
            if (cancelled) return
            cancelled = true
            task.cancel()
        }
    }

    override fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>) = synchronized(this) {
        if (cancelled) return
        if (executed) throw IllegalStateException("Task has been executed")
        executed = true
        val finalListener: TaskListener<T> = {result ->
            if (listenerCalled.compareAndSet(false, true)){
                if (!cancelled) listener(result)
            }
        }
        task.enqueue(dispatcher, finalListener)
    }

}