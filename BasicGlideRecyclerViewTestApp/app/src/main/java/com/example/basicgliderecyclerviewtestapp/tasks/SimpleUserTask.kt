package com.example.basicgliderecyclerviewtestapp.tasks

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

private val executorService = Executors.newCachedThreadPool()
private val handler = Handler(Looper.getMainLooper())

/**
 * Executing code of [callable] in a separate thread.
 * Callable results are delivered to callbacks assigned via [onSuccess] and [onError]
 */
class SimpleUserTask<T>(
    private val callable: Callable<T>
) : UserTask<T> {

    private val future: Future<*>
    private var result: UserResult<T> = PendingResult()

    init {
        future = executorService.submit {
            result = try {
                SuccessResult(callable.call())
            } catch (e: Throwable) {
                ErrorResult(e)
            }
            notifyListeners()
        }
    }

    private var valueCallback: CallBack<T>? = null
    private var errorCallback: CallBack<Throwable>? = null

    override fun onSuccess(callback: CallBack<T>): UserTask<T> {
        this.valueCallback = callback
        notifyListeners()
        return this
    }

    override fun onError(callback: CallBack<Throwable>): UserTask<T> {
        this.errorCallback = callback
        notifyListeners()
        return this
    }

    override fun cancel() {
        clear()
        future.cancel(true)
    }

    override fun await(): T {
        future.get()
        val result = this.result
        if (result is SuccessResult) return result.data
        else throw (result as ErrorResult).error
    }

    private fun notifyListeners() {
        handler.post {
            val result = this.result
            val callback = this.valueCallback
            val errorCallback = this.errorCallback
            if (result is SuccessResult && callback != null) {
                callback(result.data)
                clear()
            } else if (result is ErrorResult && errorCallback != null) {
                errorCallback.invoke(result.error)
                clear()
            }
        }
    }

    private fun clear() {
        valueCallback = null
        errorCallback = null
    }
}

