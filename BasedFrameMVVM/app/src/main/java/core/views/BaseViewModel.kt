package core.views


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import core.model.PendingResult
import core.model.Result
import core.model.tasks.Task
import core.model.tasks.TaskListener
import core.model.tasks.dispatchers.Dispatcher

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

open class BaseViewModel(
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val tasks = mutableSetOf<Task<*>>()

    override fun onCleared() {
        super.onCleared()
        clearTasks()
    }

    open fun onResult(result: Any){

    }

    fun onBackPressed(){
        clearTasks()
    }

    fun <T> Task<T>.safeEnqueue(listener: TaskListener<T>? = null){
        tasks.add(this)
        this.enqueue(dispatcher) {
            tasks.remove(this)
            listener?.invoke(it)
        }
    }

    fun <T> Task<T>.into(liveResult: MutableLiveResult<T>){
        liveResult.value = PendingResult()
        this.safeEnqueue{
            liveResult.value = it
        }
    }

    private fun clearTasks(){
        tasks.forEach { it.cancel() }
        tasks.clear()
    }


}
