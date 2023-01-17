package ru.msokolov.messengerfirebase.messages

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ru.msokolov.messengerfirebase.entities.Message
import ru.msokolov.messengerfirebase.entities.User

class MessagesViewModel(
    private val currentUserId: String,
    private val otherUserId: String
): ViewModel() {

    private val database = Firebase.database
    private val usersDBRef = database.getReference("Users")
    private val messagesDBRef = database.getReference("Messages")

    private var _messagesList: MutableLiveData<ArrayList<Message>> = MutableLiveData()
    val messagesList: LiveData<ArrayList<Message>> = _messagesList

    private var _otherUser: MutableLiveData<User> = MutableLiveData()
    val otherUser: LiveData<User> = _otherUser

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _areMessagesLoading:MutableLiveData<Boolean> = MutableLiveData(true)
    val areMessagesLoading: LiveData<Boolean> = _areMessagesLoading

    private var _areMessagesSent:MutableLiveData<Boolean> = MutableLiveData(false)
    val areMessagesSent: LiveData<Boolean> = _areMessagesSent

    fun sendMessage(message: Message){
        _areMessagesSent.value = false
        messagesDBRef
            .child(message.senderId)
            .child(message.receiverId)
            .push()
            .setValue(message)
            .addOnSuccessListener {

                //Saving message to other user after success
                messagesDBRef
                .child(message.receiverId)
                .child(message.senderId)
                .push()
                .setValue(message)
                .addOnSuccessListener {
                    _areMessagesSent.value = true
                }
                .addOnFailureListener { error ->
                    Log.d(TAG, error.message.toString())
                    _error.value = error.message
                }
            }
            .addOnFailureListener { error ->
                Log.d(TAG, error.message.toString())
                _error.value = error.message
            }
    }

    init {
        messagesDBRef.child(currentUserId).child(otherUserId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList = arrayListOf<Message>()
                    for (dataSnapshot in snapshot.children){
                        val message = dataSnapshot.getValue(Message::class.java)
                        if (message != null){
                            messagesList.add(message)
                        }
                    }
                    _messagesList.value = messagesList
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        usersDBRef.child(otherUserId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null){
                    _otherUser.value = user!!
                }
                else{
                    _areMessagesLoading.value = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.message)
                _error.value = error.message
                _areMessagesLoading.value = false
            }

        })
    }

    companion object{
        private const val TAG = "MessagesVMTAG"
    }
}