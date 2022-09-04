package core.model.tasks.factories

import core.model.tasks.AbstractTask
import core.model.tasks.SynchronizedTask
import core.model.tasks.Task
import core.model.tasks.TaskListener
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class ExecutorServiceTasksFactory(
    private val executorService: ExecutorService
):TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(ExecutorServiceTask(body))
    }

    private inner class ExecutorServiceTask<T>(
        private val body: TaskBody<T>
    ) : AbstractTask<T>(){

        private var future: Future<*>? = null

        override fun doCancel() {
            future?.cancel(true)
        }

        override fun doEnqueue(listener: TaskListener<T>) {
            future = executorService.submit {
                executeBody(body, listener)
            }
        }
    }
}