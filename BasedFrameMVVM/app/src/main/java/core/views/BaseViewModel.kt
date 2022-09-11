package core.views


import androidx.lifecycle.*
import core.model.ErrorResult
import core.model.Result
import core.model.SuccessResult
import kotlinx.coroutines.*

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<Result<T>>

open class BaseViewModel : ViewModel() {

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    protected val viewModelScope: CoroutineScope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        clearViewModelScope()
    }

    open fun onResult(result: Any){

    }

    open fun onBackPressed(): Boolean{
        clearViewModelScope()
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

    private fun clearViewModelScope(){
        viewModelScope.cancel()
    }


}
