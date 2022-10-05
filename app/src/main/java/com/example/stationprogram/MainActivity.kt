package com.example.stationprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stationprogram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addProgramButton.setOnClickListener {  }

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.station_one -> readData()
                else -> readData()
            }
            true
        }
    }

    private fun readData(){
    }
}