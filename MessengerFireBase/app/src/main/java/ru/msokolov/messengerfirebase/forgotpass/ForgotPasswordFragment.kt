package ru.msokolov.messengerfirebase.forgotpass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.msokolov.messengerfirebase.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding

    private val args by navArgs<ForgotPasswordFragmentArgs>()
    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        observeViewModel()
        binding.emailEditText.setText(args.email)
        binding.resetPasswordButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            if (email.isBlank()){
                Toast.makeText(requireContext(), "Please, fill all fields!", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                viewModel.resetPassword(email)
            }
        }
        return binding.root
    }

    private fun observeViewModel(){
        viewModel.isPasswordReset.observe(viewLifecycleOwner){  isReset ->
            if (isReset){
                findNavController().navigateUp()
                Toast.makeText(requireContext(), "Link has been successfully sent to your email"
                ,Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.error.observe(viewLifecycleOwner){ errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}