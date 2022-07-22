package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basicgliderecyclerviewtestapp.R
import com.example.basicgliderecyclerviewtestapp.UserNotFoundException
import com.example.basicgliderecyclerviewtestapp.model.UserDetails
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.tasks.EmptyResult
import com.example.basicgliderecyclerviewtestapp.tasks.PendingResult
import com.example.basicgliderecyclerviewtestapp.tasks.SuccessResult
import com.example.basicgliderecyclerviewtestapp.tasks.UserResult

class UserDetailsViewModel(private val userService: UserService) : BaseViewModel() {

    private val viewModelState = MutableLiveData<State>()
    val state: LiveData<State> = viewModelState



    private val viewModelActionShowToast = MutableLiveData<Event<Int>>()
    val actionShowToast: LiveData<Event<Int>> = viewModelActionShowToast

    private val viewModelActionGoBack = MutableLiveData<Event<Unit>>()
    val actionGoBack: LiveData<Event<Unit>> = viewModelActionGoBack


    private val currentState: State get() = state.value!!

    init {
        viewModelState.value = State(
            userDetailsResult = EmptyResult(),
            deletingInProgress = false
        )
    }

    fun loadUsers(userId: Long){
        if (currentState.userDetailsResult !is EmptyResult) return

        viewModelState.value = currentState.copy(userDetailsResult = PendingResult())

        userService.getUserDetailsById(userId)
            .onSuccess {
                viewModelState.value = currentState.copy(userDetailsResult = SuccessResult(it))
            }
            .onError {
                viewModelActionShowToast.value = Event(R.string.cant_load_user_details)
                viewModelActionGoBack.value = Event(Unit)
            }
            .autoCancel()
    }

    fun deleteUser(){
        val userDetailsResult = currentState.userDetailsResult
        if (userDetailsResult !is SuccessResult) return
        viewModelState.value = currentState.copy(deletingInProgress = true)
        userService.deleteUser(userDetailsResult.data.user)
            .onSuccess {
                viewModelActionShowToast.value = Event(R.string.user_has_been_deleted)
                viewModelActionGoBack.value = Event(Unit)
            }
            .onError {
                viewModelState.value = currentState.copy(deletingInProgress = false)
                viewModelActionShowToast.value = Event(R.string.cant_delete_user)
            }
            .autoCancel()
    }


    data class State(
        val userDetailsResult: UserResult<UserDetails>,
        private val deletingInProgress: Boolean
    ){

        val showContent: Boolean get() = userDetailsResult is SuccessResult
        val showProgress: Boolean get() = userDetailsResult is PendingResult || deletingInProgress
        val enableDeleteButton: Boolean get() = !deletingInProgress
    }
}

