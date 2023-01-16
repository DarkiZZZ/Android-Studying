package ru.msokolov.messengerfirebase.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)

        viewModel.isAuthorized.observe(viewLifecycleOwner){ isAuthorized ->
            Log.d(TAG, isAuthorized.toString())
            if (isAuthorized){
                val direction = SplashFragmentDirections.actionSplashFragmentToUserListFragment()
                findNavController().navigate(direction)
            }
            else{
                val direction = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                findNavController().navigate(direction)
            }
        }

        return binding.root
    }

    companion object{
        private const val TAG = "SplashFragmentTAG"
    }
}