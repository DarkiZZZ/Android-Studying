package com.example.basedframemvvm.views

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.example.basedframemvvm.R
import com.example.basedframemvvm.databinding.PartResultBinding
import core.model.Result
import core.views.BaseFragment


fun <T> BaseFragment.renderSimpleResult(
    root: ViewGroup,
    result: Result<T>,
    onSuccess: (T) -> Unit){

    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        result = result,
        onPending = {
            binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
            binding.errorContainer.visibility = View.VISIBLE
        },
        onSuccess = { successData ->
            root.children
                .filter { it.id != R.id.progressBar && it.id != R.id.errorContainer }
                .forEach { it.visibility = View.VISIBLE }
            onSuccess(successData)
        }
    )
}