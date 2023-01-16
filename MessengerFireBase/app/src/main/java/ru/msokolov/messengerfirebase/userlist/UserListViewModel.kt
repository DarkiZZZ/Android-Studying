package ru.msokolov.messengerfirebase.userlist

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class UserListViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun logout(){
        auth.signOut()
    }
}