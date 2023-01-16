package ru.msokolov.messengerfirebase.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel : ViewModel(){

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error
    private var _currentUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    val currentUser: LiveData<FirebaseUser> = _currentUser

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                _currentUser.value = authResult.user
            }
            .addOnFailureListener { exception ->
                _error.value = exception.message
                Log.d(TAG, exception.message.toString())

            }
    }

    companion object{
        private const val TAG = "RegisterViewModelTAG"
    }
}