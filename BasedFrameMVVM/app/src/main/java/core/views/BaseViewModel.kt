package core.views


import androidx.lifecycle.*
import core.model.ErrorResult
import core.model.Result
import core.model.SuccessResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

typealias LiveResult<T> = LiveData<Result<T>>
typealias MutableLiveResult<T> = MutableLiveData<Result<T>>

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
                if (e !is CancellationException) liveResult.postValue(ErrorResult(e))
            }
        }
    }

    fun <T> into(stateFlow: MutableStateFlow<Result<T>>, block: suspend () -> T){
        viewModelScope.launch {
            try {
                stateFlow.value = SuccessResult(block())
            }
            catch (e: Exception){
                if (e !is CancellationException) stateFlow.value = ErrorResult(e)
            }
        }
    }

    fun <T> SavedStateHandle.getMutableStateFlow(key: String, initialValue: T): MutableStateFlow<T> {
        val savedStateHandle = this
        val mutableFlow = MutableStateFlow(savedStateHandle[key] ?: initialValue)

        viewModelScope.launch {
            mutableFlow.collect {
                savedStateHandle[key] = it
            }
        }

        viewModelScope.launch {
            savedStateHandle.getLiveData<T>(key).asFlow().collect {
                mutableFlow.value = it
            }
        }

        return mutableFlow
    }

    private fun clearViewModelScope(){
        viewModelScope.cancel()
    }


}
