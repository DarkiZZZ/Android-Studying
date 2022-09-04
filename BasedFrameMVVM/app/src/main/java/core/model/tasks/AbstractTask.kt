package core.model.tasks

import core.model.ErrorResult
import core.model.FinalResult
import core.model.SuccessResult
import core.model.tasks.dispatchers.Dispatcher
import core.utils.delegates.Await

abstract class AbstractTask<T>: Task<T> {

    private var finalResult by Await<FinalResult<T>>()

    final override fun await(): T {
        val wrapperListener: TaskListener<T> = {
            finalResult = it
        }
        doEnqueue(wrapperListener)
        try {
            when(val result = finalResult){
                is ErrorResult -> throw result.exception
                is SuccessResult -> return result.data
            }
        } catch (e: Exception){
            if (e is InterruptedException){
                cancel()
                throw CancelledException(e)
            }
            else{
                throw e
            }
        }
    }

    final override fun cancel() {
        finalResult = ErrorResult(CancelledException())
        doCancel()
    }


    final override fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>) {
        val wrappedListener: TaskListener<T> = {
            finalResult = it
            dispatcher.dispatch {
                listener(finalResult)
            }
        }
        doEnqueue(wrappedListener)
    }

    fun executeBody(taskBody: TaskBody<T>, listener: TaskListener<T>){
        try {
            val data = taskBody()
            listener(SuccessResult(data))
        }
        catch (e: Exception){
            listener(ErrorResult(e))
        }
    }

    abstract fun doCancel()

    abstract fun doEnqueue(listener: TaskListener<T>)

}