package com.example.viewmodelnavigation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewmodelnavigation.databinding.FragmentHomeBinding
import com.example.viewmodelnavigation.screens.base.BaseFragment
import com.example.viewmodelnavigation.screens.base.BaseScreen
import com.example.viewmodelnavigation.screens.base.screenViewModel

class HomeFragment : BaseFragment(){

    class Screen : BaseScreen

    override val viewModel: HomeViewModel by screenViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.editButton.setOnClickListener { viewModel.onEditPressed()}

        viewModel.currentMessageLiveData.observe(viewLifecycleOwner) {
            binding.valueTextView.text = it
        }
        return binding.root
    }
}