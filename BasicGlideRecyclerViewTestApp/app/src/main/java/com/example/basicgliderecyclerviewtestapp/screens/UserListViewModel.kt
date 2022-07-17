package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener
import com.example.basicgliderecyclerviewtestapp.tasks.*

data class UserListItem(
    val user: User,
    val isInProgress: Boolean
)

class UserListViewModel(private val userService: UserService) : ViewModel() {

    private val viewModelUsers = MutableLiveData<UserResult<List<UserListItem>>>()
    var users: LiveData<UserResult<List<UserListItem>>> = viewModelUsers

    private val userIdsInProgress = mutableSetOf<Long>()
    private var usersResult: UserResult<List<User>> = EmptyResult()
        set(value) {
            field = value
            notifyUpdates()
    }

    private fun notifyUpdates() {
        viewModelUsers.postValue(  usersResult.map { viewModelUsers ->
            viewModelUsers.map { viewModelUser ->
                UserListItem(viewModelUser, isInProgress(viewModelUser)) }
        })
    }

    private val listener: UsersListener = {
        usersResult = if (it.isEmpty()){
            EmptyResult()
        } else{
            SuccessResult(it)
        }
    }

    init {
        userService.addListener { listener }
        loadUsers()
    }

    fun loadUsers(){
        usersResult = PendingResult()
        userService.loadUsers()
            .onError {
                usersResult = ErrorResult(it)
            }
    }
     private fun addProgressTo(user: User){
         userIdsInProgress.add(user.id)
     }

    private fun removeProgressFrom(user: User){

        userIdsInProgress.remove(user.id)
    }

    private fun isInProgress(user: User) : Boolean{
        return userIdsInProgress.contains(user.id)
    }

    fun relocateUser(user: User, relocation: Int){
        if (isInProgress(user)) return
        addProgressTo(user)
        userService.relocateUser(user, relocation)
            .onSuccess { removeProgressFrom(user) }
            .onError { removeProgressFrom(user) }
    }

    fun deleteUser(user: User){
        if (isInProgress(user)) return
        addProgressTo(user)
        userService.deleteUser(user)
            .onSuccess { removeProgressFrom(user) }
            .onError { removeProgressFrom(user) }
    }

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

}