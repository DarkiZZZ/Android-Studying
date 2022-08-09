package com.example.basedframemvvm.views.base

import androidx.fragment.app.Fragment
import com.example.basedframemvvm.MainActivity

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates(){
        (requireActivity() as MainActivity).notifyScreenUpdates()
    }
}
