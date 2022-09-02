package core.model.tasks

import android.os.Handler
import android.os.Looper
import core.model.ErrorResult
import core.model.FinalResult
import core.model.SuccessResult

private val handler = Handler(Looper.getMainLooper())

class SimpleTasksFactory: TasksFactory {

    override fun <T> async(body: TaskBody<T>): Task<T> {
        return SimpleTask(body)
    }

    class SimpleTask<T>(
        private val body: TaskBody<T>
    ): Task<T>{

        var thread: Thread? = null
        var cancelled = false

        override fun await(): T = body()

        override fun cancel() {
            cancelled = true
            thread?.interrupt()
            thread = null
        }

        override fun enqueue(listener: TaskListener<T>) {
            thread = Thread{
                try {
                    val data = body()
                    publishResult(listener, SuccessResult(data))
                }
                catch (e: Exception){
                    publishResult(listener, ErrorResult(e))
                }
            }.apply { start()}
        }

        private fun publishResult(listener: TaskListener<T>, result: FinalResult<T>){
            handler.post{
                if (cancelled) return@post
                listener(result)
            }
        }

    }
}