package ru.msokolov.messengerfirebase.forgotpass

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error
    private var _isPasswordReset: MutableLiveData<Boolean> = MutableLiveData(false)
    val isPasswordReset: LiveData<Boolean> = _isPasswordReset

    fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _isPasswordReset.value = true
            }
            .addOnFailureListener { exception ->
                _error.value = exception.message
                Log.d(TAG, exception.message.toString())
            }
    }

    companion object{
        private const val TAG = "ResetPassVMTAG"
    }

}