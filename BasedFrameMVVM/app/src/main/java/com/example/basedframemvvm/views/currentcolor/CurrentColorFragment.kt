package com.example.basedframemvvm.views.currentcolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basedframemvvm.databinding.FragmentCurrentColorBinding
import com.example.basedframemvvm.views.renderSimpleResult
import core.views.BaseFragment
import core.views.BaseScreen
import core.views.screenViewModel

class CurrentColorFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)

        viewModel.currentColor.observe(viewLifecycleOwner) { result ->

            renderSimpleResult(
                root = binding.root,
                result = result,
                onSuccess = {binding.colorView.setBackgroundColor(it.value)}
            )
        }

        binding.changeColorButton.setOnClickListener {
            viewModel.changeColor()
        }

        return binding.root
    }


}