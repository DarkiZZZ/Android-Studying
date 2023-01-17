package ru.msokolov.messengerfirebase.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.msokolov.messengerfirebase.MainActivity
import ru.msokolov.messengerfirebase.User
import ru.msokolov.messengerfirebase.databinding.FragmentMessagesBinding

class MessagesFragment: Fragment() {

    private lateinit var binding: FragmentMessagesBinding

    private val viewModel by viewModels<MessagesViewModel>()
    private val args by navArgs<MessagesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).supportActionBar?.title = getTitleFromUser(args.user!!)
        return binding.root
    }

    private fun getTitleFromUser(user: User): String {
        return "${user.firstName} ${user.lastName}"
    }
}