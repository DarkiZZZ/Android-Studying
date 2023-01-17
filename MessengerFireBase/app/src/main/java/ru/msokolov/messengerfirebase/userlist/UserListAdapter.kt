package ru.msokolov.messengerfirebase.userlist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.entities.User
import ru.msokolov.messengerfirebase.databinding.UserItemBinding

class UserListAdapter
    : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    var onItemClickListener: ((User) -> Unit)? = null

    private var users = arrayListOf<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(usersList: ArrayList<User>){
       users = usersList
        Log.d(TAG, users.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            UserItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UserListViewHolder(private var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                labelTextView.text = "${user.firstName} ${user.lastName}, ${user.age}"
                isOnlineImageView.background = isOnline(user, root.context)
            }
        }

        private fun isOnline(user: User, context: Context): Drawable{
            val backgroundId: Int =
            if (user.isOnline){
                R.drawable.circle_green
            }
            else{
                R.drawable.circle_red
            }
            return ContextCompat.getDrawable(context, backgroundId)!!
        }

        init {
            binding.item.setOnClickListener {
                onItemClickListener?.invoke(users[adapterPosition])
            }
        }
    }

    companion object{
        private const val TAG = "UserListAdapterTAG"
    }
}