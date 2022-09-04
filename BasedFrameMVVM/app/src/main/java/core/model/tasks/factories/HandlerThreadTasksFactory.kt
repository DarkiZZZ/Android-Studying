package core.model.tasks.factories

import android.os.Handler
import android.os.HandlerThread
import core.model.tasks.AbstractTask
import core.model.tasks.SynchronizedTask
import core.model.tasks.Task
import core.model.tasks.TaskListener

class HandlerThreadTasksFactory: TasksFactory {

    private val thread = HandlerThread(javaClass.simpleName)

    init {
        thread.start()
    }

    private val handler = Handler(thread.looper)

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SynchronizedTask(HandlerThreadTask(body))
    }

    private inner class HandlerThreadTask<T>(
        private val body: TaskBody<T>
    ) : AbstractTask<T>() {

        private var thread: Thread? = null

        override fun doCancel() {
            thread?.interrupt()
        }

        override fun doEnqueue(listener: TaskListener<T>) {
            val runnable = Runnable {
                thread = Thread{
                    executeBody(body, listener)
                }
                thread?.start()
            }
            handler.post(runnable)
        }
    }
}