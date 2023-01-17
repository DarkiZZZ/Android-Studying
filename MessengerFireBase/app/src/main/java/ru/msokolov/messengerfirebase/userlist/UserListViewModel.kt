package ru.msokolov.messengerfirebase.userlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.msokolov.messengerfirebase.User

class UserListViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val database = Firebase.database
    private val usersDBRef = database.getReference("Users")

    private var _usersListFirebaseDB: MutableLiveData<ArrayList<User>> = MutableLiveData()
    val usersListFirebaseDB: LiveData<ArrayList<User>> = _usersListFirebaseDB

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _isDataLoading:MutableLiveData<Boolean> = MutableLiveData(true)
    val isDataLoading: LiveData<Boolean> = _isDataLoading

    fun logout(){
        auth.signOut()
    }

    fun getUsersFromFireBaseDB(){
        usersDBRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                _isDataLoading.value = true
                val currentUser = auth.currentUser ?: return

                val usersFromDB = arrayListOf<User>()
                for (dataSnapshot in snapshot.children){
                    val user = dataSnapshot.getValue(User::class.java)?: return
                    if (user.id != currentUser.uid){
                        usersFromDB.add(user)
                    }
                }
                _usersListFirebaseDB.value = usersFromDB
                Log.d(TAG, usersFromDB.toString())
                _isDataLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _error.value = error.message
                _isDataLoading.value = false
            }
        })
    }

    companion object{
        private const val TAG = "UserListVMTAG"
    }
}