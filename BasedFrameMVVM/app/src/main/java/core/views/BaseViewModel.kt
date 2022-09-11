package core.views


import androidx.lifecycle.*
import core.model.ErrorResult
import core.model.Result
import core.model.SuccessResult
import core.model.tasks.Task
import kotlinx.coroutines.launch

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

open class BaseViewModel : ViewModel() {

    private val tasks = mutableSetOf<Task<*>>()

    override fun onCleared() {
        super.onCleared()
        clearTasks()
    }

    open fun onResult(result: Any){

    }

    open fun onBackPressed(): Boolean{
        clearTasks()
        return false
    }

    fun <T> into(liveResult: MutableLiveResult<T>, block: suspend () -> T){
        viewModelScope.launch {
            try {
                liveResult.postValue(SuccessResult(block()))
            }
            catch (e: Exception){
                liveResult.postValue(ErrorResult(e))
            }
        }
    }

    private fun clearTasks(){
        tasks.forEach { it.cancel() }
        tasks.clear()
    }


}
