package ru.msokolov.messengerfirebase.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.msokolov.messengerfirebase.entities.User

class RegisterViewModel : ViewModel(){

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val database = Firebase.database
    private val usersDBRef = database.getReference("Users")

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
                val firebaseUser = authResult.user ?: return@addOnSuccessListener
                val id = firebaseUser.uid
                val userToDB = User(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    age = age,
                    online = true
                )
                usersDBRef.child(id).setValue(userToDB)
                //
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