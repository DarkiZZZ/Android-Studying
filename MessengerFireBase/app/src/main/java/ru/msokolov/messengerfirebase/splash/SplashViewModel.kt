package ru.msokolov.messengerfirebase.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _isAuthorized: MutableLiveData<Boolean> = MutableLiveData()
    val isAuthorized: LiveData<Boolean> = _isAuthorized

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _isAuthorized.value = firebaseAuth.currentUser != null
        }
    }
}