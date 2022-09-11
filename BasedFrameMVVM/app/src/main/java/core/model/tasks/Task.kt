package core.model.tasks

import core.model.ErrorResult
import core.model.FinalResult
import core.model.SuccessResult
import core.model.tasks.dispatchers.Dispatcher
import core.model.tasks.dispatchers.ImmediateDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

typealias TaskListener<T> = (FinalResult<T>) -> Unit

class CancelledException(
    originException:Exception? = null
): Exception(originException)

interface Task<T> {

    fun await(): T

    fun cancel()

    fun enqueue(dispatcher: Dispatcher, listener: TaskListener<T>)

    //Listeners are called only in main thread

    suspend fun suspend(): T = suspendCancellableCoroutine { continuation ->
        enqueue(ImmediateDispatcher()){
            continuation.invokeOnCancellation { cancel() }
            when(it){
                is SuccessResult -> continuation.resume(it.data)
                is ErrorResult -> continuation.resumeWithException(it.exception)
            }

        }

    }
}