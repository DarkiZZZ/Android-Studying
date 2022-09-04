package core.model.tasks.factories

import core.model.tasks.AbstractTask
import core.model.tasks.SynchronizedTask
import core.model.tasks.Task
import core.model.tasks.TaskListener

class ThreadTasksFactory: TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(ThreadTask(body))
    }

    private class ThreadTask<T>(
        private val body: TaskBody<T>
    ): AbstractTask<T>() {

        private var thread: Thread? = null

        override fun doCancel() {
            thread?.interrupt()
        }

        override fun doEnqueue(listener: TaskListener<T>) {
            thread = Thread{
                executeBody(body, listener)
            }
            thread?.start()
        }

    }
}