package ru.msokolov.messengerfirebase.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        observeViewModel()
        setupClickListeners(binding)
        return binding.root
    }

    private fun observeViewModel(){
        viewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                Toast.makeText(requireContext(), "Authorized", Toast.LENGTH_SHORT)
                    .show()
                findNavController()
                    .navigate(R.id.action_signInFragment_to_userListFragment)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupClickListeners(binding: FragmentSignInBinding){
        binding.forgotPasswordTextView.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            findNavController()
                .navigate(
                    SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment(
                        email
                    )
                )
        }
        binding.registerTextView.setOnClickListener {
            findNavController()
                .navigate(R.id.action_signInFragment_to_registerFragment)
        }
        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            viewModel.login(email, password)
        }
    }
}