package ru.msokolov.messengerfirebase.messages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.msokolov.messengerfirebase.MainActivity
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.entities.User
import ru.msokolov.messengerfirebase.databinding.FragmentMessagesBinding
import ru.msokolov.messengerfirebase.entities.Message

class MessagesFragment: Fragment() {

    private lateinit var binding: FragmentMessagesBinding

    private val args by navArgs<MessagesFragmentArgs>()
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var viewModelFactory: MessagesViewModelFactory
    private lateinit var viewModel: MessagesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = MessagesViewModelFactory(
            args.currentUserId,
            args.otherUser.id)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MessagesViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(layoutInflater, container, false)
        observeViewModel()
        initAdapter()
        (requireActivity() as MainActivity).supportActionBar?.title = getTitleFromUser(args.otherUser)
        binding.sendImageView.setOnClickListener {
            val text = binding.messageEditText.text.toString().trim()
            if (text.isBlank()){
                return@setOnClickListener
            }
            else {
                val message = Message(
                    text,
                    args.currentUserId,
                    args.otherUser.id
                )
                viewModel.sendMessage(message)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.setIsUserOnline(isOnline = true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setIsUserOnline(isOnline = false)
    }

    private fun initAdapter(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        messagesAdapter = MessagesAdapter()
        messagesAdapter.currentUserId = args.currentUserId
        binding.recyclerView.adapter = messagesAdapter
    }

    private fun getTitleFromUser(user: User): String {
        return "${user.firstName} ${user.lastName}"
    }

    private fun observeViewModel(){
        viewModel.error.observe(viewLifecycleOwner){ errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        viewModel.areMessagesLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading){
                binding.progressBar.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.areMessagesSent.observe(viewLifecycleOwner){ isSent ->
            if (isSent){
                binding.messageEditText.text.clear()
            }
        }
        viewModel.messagesList.observe(viewLifecycleOwner){ messagesList ->
            messagesAdapter.setMessagesList(messagesList)
        }
        viewModel.isUserOnline.observe(viewLifecycleOwner){ isOnline ->
            Log.d(TAG, isOnline.toString())
            if (isOnline){
                binding.apply {
                    isOnlineTextView.text = resources.getString(R.string.online)
                    isOnlineView.background = ContextCompat
                        .getDrawable(requireContext(),R.drawable.circle_green)
                }
            }
            else{
                binding.apply {
                    isOnlineTextView.text = resources.getString(R.string.offline)
                    isOnlineView.background = ContextCompat
                        .getDrawable(requireContext(),R.drawable.circle_red)
                }
            }
        }
    }

    companion object{
        private const val TAG = "MessagesFragmentTAG"
    }
}