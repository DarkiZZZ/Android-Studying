package com.example.basicgliderecyclerviewtestapp.tasks

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

private val executorService = Executors.newCachedThreadPool()
private val handler = Handler(Looper.getMainLooper())

class SimpleUserTask<T>(
    private val callable: Callable<T>) : UserTask<T> {

    private val future: Future<*>
    private var result: UserResult<T> = PendingResult()

    init {
        future = executorService.submit{
            result = try {
                SuccessResult(callable.call())
            }
            catch (e: Throwable){
                ErrorResult(e)
            }
        }
    }

    private var valueCallback: CallBack<T>? = null
    private var errorCallback: CallBack<Throwable>? = null


    override fun onSuccess(callBack: CallBack<T>): UserTask<T> {
       valueCallback = callBack
        notifyListeners()
        return this
    }

    private fun notifyListeners() {
        handler.post{
            val result = this.result
            val callBack = this.valueCallback
            val errorCallback = this.errorCallback
            if (result is SuccessResult && callBack != null) {
                callBack(result.data)
                clear()
            } else if (result is ErrorResult && errorCallback != null){
                errorCallback.invoke(result.error)
                clear()
            }
        }
    }

    override fun onError(callBack: CallBack<Throwable>): UserTask<T> {
        errorCallback = callBack
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

    private fun clear(){
        valueCallback = null
        errorCallback = null
    }
}