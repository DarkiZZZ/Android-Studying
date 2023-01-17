package ru.msokolov.messengerfirebase.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MessagesViewModelFactory(
    private val currentUserId: String,
    private val otherUserId: String) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /*if (modelClass.isAssignableFrom(MessagesFragment::class.java)){
            return MessagesViewModel(currentUserId, otherUserId) as T
        }
        throw IllegalStateException("Unknown ViewModel Class in VM Factory")*/

        return modelClass.getConstructor(String::class.java)
            .newInstance(currentUserId, otherUserId)
    }
}