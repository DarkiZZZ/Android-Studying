package com.example.basicgliderecyclerviewtestapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicgliderecyclerviewtestapp.UserActionListener
import com.example.basicgliderecyclerviewtestapp.UserAdapter
import com.example.basicgliderecyclerviewtestapp.databinding.FragmentUserListBinding
import com.example.basicgliderecyclerviewtestapp.model.User


class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        adapter = UserAdapter(object : UserActionListener {
            override fun onUserRelocate(user: User, relocation: Int) {
                TODO("Not yet implemented")
            }

            override fun onUserDelete(user: User) {
                TODO("Not yet implemented")
            }

            override fun onUserDetails(user: User) {
                TODO("Not yet implemented")
            }

        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}