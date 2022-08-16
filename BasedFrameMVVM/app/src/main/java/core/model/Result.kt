package core.model

import java.lang.Exception

sealed class Result<T>

class PendingResult<T> : Result<T>()

class SuccessResult<T>(
    val data: T
) : Result<T>()

class ErrorResult<T>(
    val exception: Exception
) : Result<T>()

