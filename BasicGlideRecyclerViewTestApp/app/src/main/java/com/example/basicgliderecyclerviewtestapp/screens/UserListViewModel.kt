package com.example.basicgliderecyclerviewtestapp.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicgliderecyclerviewtestapp.R
import com.example.basicgliderecyclerviewtestapp.UserActionListener
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserDetails
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener
import com.example.basicgliderecyclerviewtestapp.tasks.*

data class UserListItem(
    val user: User,
    val isInProgress: Boolean
)

class UserListViewModel(private val userService: UserService)
    : BaseViewModel(), UserActionListener {

    private val viewModelUsers = MutableLiveData<UserResult<List<UserListItem>>>()
    var users: LiveData<UserResult<List<UserListItem>>> = viewModelUsers

    private val viewModelActionShowToast = MutableLiveData<Event<Int>>()
    val actionShowToast: LiveData<Event<Int>> = viewModelActionShowToast

    private val viewModelActionShowDetails = MutableLiveData<Event<User>>()
    val actionShowDetails : LiveData<Event<User>> = viewModelActionShowDetails

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
            .autoCancel()
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


    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }


    override fun onUserRelocate(user: User, relocation: Int) {
        if (isInProgress(user)) return
        addProgressTo(user)
        userService.relocateUser(user, relocation)
            .onSuccess { removeProgressFrom(user) }
            .onError {
                removeProgressFrom(user)
                viewModelActionShowToast.value = Event(R.string.cant_move_user)
            }
            .autoCancel()
    }

    override fun onUserDelete(user: User) {
        if (isInProgress(user)) return
        addProgressTo(user)
        userService.deleteUser(user)
            .onSuccess { removeProgressFrom(user) }
            .onError {
                removeProgressFrom(user)
                viewModelActionShowToast.value = Event(R.string.cant_delete_user)
            }
            .autoCancel()
    }

    override fun onUserDetails(user: User) {
        viewModelActionShowDetails.value = Event(user)
    }

}