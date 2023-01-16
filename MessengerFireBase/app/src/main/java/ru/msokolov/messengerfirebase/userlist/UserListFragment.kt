package ru.msokolov.messengerfirebase.userlist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.msokolov.messengerfirebase.R
import ru.msokolov.messengerfirebase.User
import ru.msokolov.messengerfirebase.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListAdapter: UserListAdapter
    private val viewModel by viewModels<UserListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        initAdapter()
        viewModel.getUsersFromFireBaseDB()
        observeViewModel()
        setHasOptionsMenu(true)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout){
            viewModel.logout()
            findNavController().navigate(R.id.action_userListFragment_to_signInFragment)
        }
        return true
    }

    private fun initAdapter(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userListAdapter = UserListAdapter()
        binding.recyclerView.adapter = userListAdapter

        userListAdapter.onItemClickListener = { user ->
            //TODO
        }
    }

    private fun observeViewModel(){
        viewModel.error.observe(viewLifecycleOwner){ errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
        viewModel.usersListFirebaseDB.observe(viewLifecycleOwner){ users ->
            userListAdapter.setUserList(users as ArrayList<User>)
        }
    }
}