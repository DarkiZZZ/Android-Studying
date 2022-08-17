package com.example.basedframemvvm.views.currentcolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basedframemvvm.databinding.FragmentCurrentColorBinding
import com.example.basedframemvvm.databinding.PartResultBinding
import core.model.ErrorResult
import core.model.PendingResult
import core.model.SuccessResult
import core.views.BaseFragment
import core.views.BaseScreen
import core.views.screenViewModel

class CurrentColorFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        val resultBinding = PartResultBinding.bind(binding.root)

        viewModel.currentColor.observe(viewLifecycleOwner) { result ->
            renderResult(
                root = binding.root,
                result = result,
                onSuccess = {binding.colorContainer.visibility = View.VISIBLE
                    binding.changeColorButton.visibility = View.VISIBLE
                    binding.colorView.setBackgroundColor(it.value) },
                onError = {resultBinding.errorContainer.visibility = View.VISIBLE},
                onPending = {resultBinding.progressBar.visibility = View.VISIBLE}
            )
            /*when(result){
                is PendingResult ->{

                    resultBinding.errorContainer.visibility = View.GONE
                    binding.colorContainer.visibility = View.GONE
                    binding.changeColorButton.visibility = View.GONE
                }
                is ErrorResult ->{
                    resultBinding.progressBar.visibility = View.GONE
                    binding.colorContainer.visibility = View.GONE
                    binding.changeColorButton.visibility = View.GONE
                }
                is SuccessResult ->{
                    resultBinding.progressBar.visibility = View.GONE
                    resultBinding.errorContainer.visibility = View.GONE

                }
            }*/
        }

        binding.changeColorButton.setOnClickListener {
            viewModel.changeColor()
        }

        return binding.root
    }


}