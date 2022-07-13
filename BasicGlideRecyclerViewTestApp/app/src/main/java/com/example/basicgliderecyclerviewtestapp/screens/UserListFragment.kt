package com.example.basicgliderecyclerviewtestapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicgliderecyclerviewtestapp.UserActionListener
import com.example.basicgliderecyclerviewtestapp.UserAdapter
import com.example.basicgliderecyclerviewtestapp.databinding.FragmentUserListBinding
import com.example.basicgliderecyclerviewtestapp.model.User


class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserAdapter

    private val viewModel: UserListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        adapter = UserAdapter(object : UserActionListener {
            override fun onUserRelocate(user: User, relocation: Int) {
                viewModel.relocateUser(user, relocation)
            }

            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                navigator().showDetails(user)
            }

        })

        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.users = it
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}