package ru.msokolov.messengerfirebase.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        viewModel.currentUser.observe(viewLifecycleOwner){ firebaseUser ->
            if (firebaseUser != null) {
                Toast.makeText(requireContext(), "Account created successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController()
                    .navigate(R.id.action_registerFragment_to_userListFragment)
            }
        }
        viewModel.error.observe(viewLifecycleOwner){ errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.signUpButton.setOnClickListener {
            binding.apply {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val firstName = firstNameEditText.text.toString().trim()
                val lastName = lastNameEditText.text.toString().trim()
                val age = ageEditText.text.toString().trim()
                if (email.isBlank()
                    || password.isBlank()
                    || firstName.isBlank()
                    || lastName.isBlank()
                    || age.isBlank()){
                    Toast.makeText(requireContext(), "Please, fill all fields!", Toast.LENGTH_SHORT)
                        .show()
                    }
                else{
                    viewModel.register(
                        email,
                        password,
                        firstName,
                        lastName,
                        age.toInt()
                    )
                }
            }
        }
        return binding.root
    }

}