package ru.msokolov.messengerfirebase.userlist

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

    private var _usersListFirebaseDB: MutableLiveData<List<User>> = MutableLiveData()
    val usersListFirebaseDB: LiveData<List<User>> = _usersListFirebaseDB
    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    fun logout(){
        auth.signOut()
    }

    fun getUsersFromFireBaseDB(){
        usersDBRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = auth.currentUser ?: return

                val usersFromDB = arrayListOf<User>()
                for (dataSnapshot in snapshot.children){
                    val user = dataSnapshot.getValue(User::class.java)?: return
                    if (user.id != currentUser.uid){
                        usersFromDB.add(user)
                    }
                }
                _usersListFirebaseDB.value = usersFromDB
            }

            override fun onCancelled(error: DatabaseError) {
                _error.value = error.message
            }
        })
    }
}