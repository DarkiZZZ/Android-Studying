package com.example.basicgliderecyclerviewtestapp.tasks

sealed class UserResult<T>

class SuccessResult<T>(
    val data: T,
) : UserResult<T>()

class ErrorResult<T>(
    val error: Throwable
) : UserResult<T>()

class PendingResult<T> : UserResult<T>()

class EmptyResult<T> : UserResult<T>()