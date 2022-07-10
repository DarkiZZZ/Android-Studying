package com.example.basicgliderecyclerviewtestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.basicgliderecyclerviewtestapp.databinding.ItemUserBinding
import com.example.basicgliderecyclerviewtestapp.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var users : List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UserViewHolder(val binding: ItemUserBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding){
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
                userPhotoImageView.setImageResource(R.drawable.ic_user_photo)
            }
        }
    }

    override fun getItemCount(): Int = users.size
}