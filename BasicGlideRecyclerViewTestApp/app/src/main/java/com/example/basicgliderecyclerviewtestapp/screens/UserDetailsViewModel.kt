package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basicgliderecyclerviewtestapp.R
import com.example.basicgliderecyclerviewtestapp.model.UserDetails
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.tasks.EmptyResult
import com.example.basicgliderecyclerviewtestapp.tasks.PendingResult
import com.example.basicgliderecyclerviewtestapp.tasks.SuccessResult
import com.example.basicgliderecyclerviewtestapp.tasks.UserResult

class UserDetailsViewModel(private val userService: UserService) : BaseViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _actionShowToast = MutableLiveData<Event<Int>>()
    val actionShowToast: LiveData<Event<Int>> = _actionShowToast

    private val _actionGoBack = MutableLiveData<Event<Unit>>()
    val actionGoBack: LiveData<Event<Unit>> = _actionGoBack

    private val currentState: State get() = state.value!!

    init {
        _state.value = State(
            userDetailsResult = EmptyResult(),
            deletingInProgress = false
        )
    }

    fun loadUsers(userId: Long) {
        // Do not start 2nd load user details request if we rotate
        // screen during loading details
        if (currentState.userDetailsResult !is EmptyResult) return

        _state.value = currentState.copy(userDetailsResult = PendingResult())

        userService.getById(userId)
            .onSuccess {
                _state.value = currentState.copy(userDetailsResult = SuccessResult(it))
            }
            .onError {
                _actionShowToast.value = Event(R.string.cant_load_user_details)
                _actionGoBack.value = Event(Unit)
            }
            .autoCancel()
    }

    fun deleteUser() {
        val userDetailsResult = currentState.userDetailsResult
        if (userDetailsResult !is SuccessResult) return
        _state.value = currentState.copy(deletingInProgress = true)
        userService.deleteUser(userDetailsResult.data.user)
            .onSuccess {
                _actionShowToast.value = Event(R.string.user_has_been_deleted)
                _actionGoBack.value = Event(Unit)
            }
            .onError {
                _state.value = currentState.copy(deletingInProgress = false)
                _actionShowToast.value = Event(R.string.cant_delete_user)
            }
            .autoCancel()
    }


    data class State(
        val userDetailsResult: UserResult<UserDetails>,
        private val deletingInProgress: Boolean
    ) {

        val showContent: Boolean get() = userDetailsResult is SuccessResult
        val showProgress: Boolean get() = userDetailsResult is PendingResult || deletingInProgress
        val enableDeleteButton: Boolean get() = !deletingInProgress

    }
}

