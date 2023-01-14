package ru.msokolov.messengerfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.msokolov.messengerfirebase.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        binding.forgotPasswordTextView.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            if (email.isBlank()){
                findNavController()
                    .navigate(R.id.action_signInFragment_to_forgotPasswordFragment)
            }
            else{
                findNavController()
                    .navigate(SignInFragmentDirections
                    .actionSignInFragmentToForgotPasswordFragment(email))
            }
        }
        return binding.root
    }
}