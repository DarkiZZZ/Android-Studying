package com.example.basicgliderecyclerviewtestapp.tasks

sealed class UserResult<T>{

    fun <R> map(mapper: (T) -> R): UserResult<R> {
        if(this is SuccessResult) return SuccessResult(mapper(data))
        return this as UserResult<R>
    }

}

class SuccessResult<T>(
    val data: T,
) : UserResult<T>()

class ErrorResult<T>(
    val error: Throwable
) : UserResult<T>()

class PendingResult<T> : UserResult<T>()

class EmptyResult<T> : UserResult<T>()