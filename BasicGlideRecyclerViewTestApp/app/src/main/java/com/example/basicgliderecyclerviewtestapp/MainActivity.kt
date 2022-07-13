package com.example.basicgliderecyclerviewtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicgliderecyclerviewtestapp.databinding.ActivityMainBinding
import com.example.basicgliderecyclerviewtestapp.model.User
import com.example.basicgliderecyclerviewtestapp.model.UserService
import com.example.basicgliderecyclerviewtestapp.model.UsersListener
import com.example.basicgliderecyclerviewtestapp.screens.Navigator
import com.example.basicgliderecyclerviewtestapp.screens.UserDetailsFragment
import com.example.basicgliderecyclerviewtestapp.screens.UserListFragment

class MainActivity : AppCompatActivity(), Navigator{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragmentContainer, UserListFragment())
                .commit()
        }
    }

    override fun showDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user.id))
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showToast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }


}