package core.model

import kotlinx.coroutines.CancellableContinuation
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun <T> CancellableContinuation<T>.toEmitter(): Emitter<T>{
    return object : Emitter<T> {

        var isDone = AtomicBoolean(false)

        override fun emit(finalResult: FinalResult<T>) {
            if (isDone.compareAndSet(false, true)) {
                when (finalResult) {
                    is SuccessResult -> resume(finalResult.data)
                    is ErrorResult -> resumeWithException(finalResult.exception)
                }
            }
        }

        override fun setCancelListener(cancelListener: CancelListener) {
           invokeOnCancellation { cancelListener() }
        }

    }

}