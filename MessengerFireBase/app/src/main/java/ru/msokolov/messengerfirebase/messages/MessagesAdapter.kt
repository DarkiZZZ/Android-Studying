package ru.msokolov.messengerfirebase.messages

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.entities.Message

class MessagesAdapter
    : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    lateinit var currentUserId: String

    private var messages = arrayListOf<Message>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMessagesList(messagesList: ArrayList<Message>){
        messages = messagesList
        Log.d(TAG, messages.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {

        val layoutResId = when(viewType){
                VIEW_TYPE_MY_MESSAGE -> R.layout.my_message_item
                else -> R.layout.other_message_item
            }
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
        return MessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == currentUserId){
            VIEW_TYPE_MY_MESSAGE
        } else{
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun getItemCount(): Int = messages.size

    inner class MessagesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(message: Message){
            textView.text = message.text
        }
    }

    companion object{
        private const val TAG = "UserListAdapterTAG"
        private const val VIEW_TYPE_MY_MESSAGE = 1
        private const val VIEW_TYPE_OTHER_MESSAGE = 2
    }
}