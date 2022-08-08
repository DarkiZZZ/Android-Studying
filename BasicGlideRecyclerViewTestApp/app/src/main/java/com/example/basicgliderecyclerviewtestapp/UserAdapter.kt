package com.example.basicgliderecyclerviewtestapp

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.CustomPopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicgliderecyclerviewtestapp.databinding.ItemUserBinding
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserListItem

interface UserActionListener {

    fun onUserMove(user: User, moveBy: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user: User)

}

class UserDiffCallback(
    private val oldList: List<UserListItem>,
    private val newList: List<UserListItem>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.user.id == newUser.user.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.user == newUser.user
    }

}

class UserAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>(), View.OnClickListener {

    var users: List<UserListItem> = emptyList()
        set(newValue) {
            val diffCallBack = UserDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.popupMenuImageView -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.popupMenuImageView.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val userListItem = users[position]
        val user = userListItem.user

        with(holder.binding) {
            holder.itemView.tag = user
            popupMenuImageView.tag = user

            if (userListItem.isInProgress) {
                popupMenuImageView.visibility = View.INVISIBLE
                itemProgressBar.visibility = View.VISIBLE
                holder.binding.root.setOnClickListener(null)
            } else {
                popupMenuImageView.visibility = View.VISIBLE
                itemProgressBar.visibility = View.GONE
                holder.binding.root.setOnClickListener(this@UserAdapter)
            }

            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            if (user.photo.isNotBlank()) {
                Glide.with(userPhotoImageView.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_photo)
                    .error(R.drawable.ic_user_photo)
                    .into(userPhotoImageView)
            } else {
                Glide.with(userPhotoImageView.context).clear(userPhotoImageView)
                userPhotoImageView.setImageResource(R.drawable.ic_user_photo)
                // you can also use the following code instead of these two lines ^
                // Glide.with(photoImageView.context)
                //        .load(R.drawable.ic_user_avatar)
                //        .into(photoImageView)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = CustomPopupMenu(view.context, view)
        val context = view.context
        val user = view.tag as User
        val position = users.indexOfFirst { it.user.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
            setIcon(R.drawable.ic_up)
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
            isEnabled = position < users.size - 1
            setIcon(R.drawable.ic_down)
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove)).apply {
            setIcon(R.drawable.ic_delete)
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user, 1)
                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    class UsersViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}