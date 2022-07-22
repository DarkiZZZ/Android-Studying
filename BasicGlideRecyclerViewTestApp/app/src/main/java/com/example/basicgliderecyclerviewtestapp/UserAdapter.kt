package com.example.basicgliderecyclerviewtestapp

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicgliderecyclerviewtestapp.databinding.ItemUserBinding
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.screens.UserListItem

interface UserActionListener{
    fun onUserRelocate(user: User, relocation: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user: User)
}

class UserAdapter(private val actionListener : UserActionListener)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>(), View.OnClickListener {

    var users : List<UserListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UserViewHolder(val binding: ItemUserBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.popupMenuImageView.setOnClickListener(this)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userListItem = users[position]
        val user = userListItem.user

        with(holder.binding){
            holder.itemView.tag = user
            popupMenuImageView.tag = user

            if (userListItem.isInProgress){
                popupMenuImageView.visibility = View.INVISIBLE
                itemProgressBar.visibility = View.VISIBLE
                holder.binding.root.setOnClickListener(null)
            }
            else{
                popupMenuImageView.visibility = View.VISIBLE
                itemProgressBar.visibility = View.GONE
                holder.binding.root.setOnClickListener(this@UserAdapter)
            }

            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            if (user.photo.isNotBlank()){
                Glide.with(userPhotoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_photo)
                    .error(R.drawable.ic_user_photo)
                    .into(userPhotoImageView)
            }
            else{
                Glide.with(userPhotoImageView.context).clear(userPhotoImageView)
                userPhotoImageView.setImageResource(R.drawable.ic_user_photo)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    override fun onClick(view: View) {
        val user = view.tag as User
        when(view.id){
            R.id.popupMenuImageView ->{
                showPopupMenu(view)
            }
            else ->{
                actionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val user = view.tag as User
        val position = users.indexOfFirst { it.user.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
            isEnabled = position < users.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserRelocate(user, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserRelocate(user, 1)
                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}