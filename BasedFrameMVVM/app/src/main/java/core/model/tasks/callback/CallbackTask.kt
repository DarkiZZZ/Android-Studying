package core.model.tasks.callback

import core.model.tasks.AbstractTask
import core.model.tasks.TaskListener

class CallbackTask<T> private constructor(
    private val executionListener: ExecutionListener<T>
): AbstractTask<T>(){

    private val emitter: EmitterImpl<T>? = null

    override fun doCancel() {
        TODO("Not yet implemented")
    }

    override fun doEnqueue(listener: TaskListener<T>) {
        emitter = EmitterImpl(listener).also{executionListener(it)}
    }
}