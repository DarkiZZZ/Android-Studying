package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basicgliderecyclerviewtestapp.R
import com.example.basicgliderecyclerviewtestapp.UserActionListener
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserListItem
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener
import com.example.basicgliderecyclerviewtestapp.tasks.*



class UserListViewModel(private val userService: UserService)
    : BaseViewModel(), UserActionListener {

    private val _users = MutableLiveData<UserResult<List<UserListItem>>>()
    val users: LiveData<UserResult<List<UserListItem>>> = _users

    private val _actionShowDetails = MutableLiveData<Event<User>>()
    val actionShowDetails: LiveData<Event<User>> = _actionShowDetails

    private val _actionShowToast = MutableLiveData<Event<Int>>()
    val actionShowToast: LiveData<Event<Int>> = _actionShowToast

    private val userIdsInProgress = mutableSetOf<Long>()
    private var usersResult: UserResult<List<User>> = EmptyResult()
        set(value) {
            field = value
            notifyUpdates()
        }

    private val listener: UsersListener = {
        usersResult = if (it.isEmpty()) {
            EmptyResult()
        } else {
            SuccessResult(it)
        }
    }

    init {
        userService.addListener(listener)
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

    fun loadUsers() {
        usersResult = PendingResult()
        userService.loadUsers()
            .onError {
                usersResult = ErrorResult(it)
            }
            .autoCancel()

    }

    override fun onUserMove(user: User, moveBy: Int) {
        if (isInProgress(user)) return
        addProgressTo(user)
        with(userService) {
            moveUser(user, moveBy)
                .onSuccess {
                    removeProgressFrom(user)
                }
                .onError {
                    removeProgressFrom(user)
                    _actionShowToast.value = Event(R.string.cant_move_user)
                }
                .autoCancel()
        }
    }

    override fun onUserDelete(user: User) {
        if (isInProgress(user)) return
        addProgressTo(user)
        userService.deleteUser(user)
            .onSuccess {
                removeProgressFrom(user)
            }
            .onError {
                removeProgressFrom(user)
                _actionShowToast.value = Event(R.string.cant_delete_user)
            }
            .autoCancel()
    }

    override fun onUserDetails(user: User) {
        _actionShowDetails.value = Event(user)
    }

    private fun addProgressTo(user: User) {
        userIdsInProgress.add(user.id)
        notifyUpdates()
    }

    private fun removeProgressFrom(user: User) {
        userIdsInProgress.remove(user.id)
        notifyUpdates()
    }

    private fun isInProgress(user: User): Boolean {
        return userIdsInProgress.contains(user.id)
    }

    private fun notifyUpdates() {
        _users.postValue(  usersResult.map { viewModelUsers ->
            viewModelUsers.map { viewModelUser ->
                UserListItem(viewModelUser, isInProgress(viewModelUser)) }
        })
    }
}