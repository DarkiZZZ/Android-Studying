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
import com.example.basicgliderecyclerviewtestapp.tasks.EmptyResult
import com.example.basicgliderecyclerviewtestapp.tasks.ErrorResult
import com.example.basicgliderecyclerviewtestapp.tasks.PendingResult
import com.example.basicgliderecyclerviewtestapp.tasks.SuccessResult


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
        adapter = UserAdapter(viewModel)

        viewModel.users.observe(viewLifecycleOwner, Observer {
            hideAllUi()
            when(it){
                is SuccessResult -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.users = it.data
                }
                is ErrorResult -> {
                    binding.tryAgainContainer.visibility = View.VISIBLE
                }
                is PendingResult -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is EmptyResult -> {
                    binding.noUsersTextView.visibility = View.VISIBLE
                }
            }
        })

        viewModel.actionShowDetails.observe(viewLifecycleOwner, Observer {
            it.getValue()?.let { user -> navigator().showDetails(user) }
        })
        viewModel.actionShowToast.observe(viewLifecycleOwner, Observer {
            it.getValue()?.let { messageRes -> navigator().showToast(messageRes) }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun hideAllUi(){
        binding.recyclerView.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.tryAgainContainer.visibility = View.GONE
        binding.noUsersTextView.visibility = View.GONE
    }
}