package com.example.tictactoecustomviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoecustomviewapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root) }

        binding.customView.ticTacToeField = TicTacToeField(10 ,10 )
        binding.buttonRandomField.setOnClickListener {
            binding.customView.ticTacToeField = TicTacToeField(
                Random.nextInt(3, 10),
                Random.nextInt(3, 10)
            )
            for (row in 0..binding.customView.ticTacToeField!!.rows){
                for (column in 0..binding.customView.ticTacToeField!!.columns){
                    if(Random.nextBoolean()){
                        binding.customView.ticTacToeField!!.setCell(row, column, Cell.CROSS)
                    }
                    else{
                        binding.customView.ticTacToeField!!.setCell(row, column, Cell.ZERO)
                    }
                }
            }
        }
    }
}