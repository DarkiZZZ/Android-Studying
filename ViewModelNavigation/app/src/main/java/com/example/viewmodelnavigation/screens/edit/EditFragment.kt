package com.example.viewmodelnavigation.screens.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewmodelnavigation.databinding.FragmentEditBinding
import com.example.viewmodelnavigation.screens.base.BaseFragment
import com.example.viewmodelnavigation.screens.base.BaseScreen
import com.example.viewmodelnavigation.screens.base.screenViewModel

class EditFragment : BaseFragment() {

    class Screen(
        val initialValue: String
    ) : BaseScreen

    override val viewModel: EditViewModel by screenViewModel<EditViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditBinding.inflate(layoutInflater, container, false)

        viewModel.initialMessageEvent.observe(viewLifecycleOwner){
            it.getValue()?.let { message -> binding.valueEditText.setText(message)}
        }

        binding.saveButton.setOnClickListener {
            viewModel.onSavePressed(binding.valueEditText.text.toString())
        }

        binding.cancelButton.setOnClickListener {
            viewModel.onCancelPressed()
        }

        return binding.root
    }
}