package com.example.basicgliderecyclerviewtestapp.tasks

typealias CallBack<T> = (T) -> Unit

interface UserTask<T> {

    fun onSuccess(callBack: CallBack<T>) : UserTask<T>

    fun onError(callBack: CallBack<Throwable>) : UserTask<T>

    fun cancel()

    fun await() : T
}