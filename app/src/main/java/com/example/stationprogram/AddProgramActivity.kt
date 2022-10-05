package com.example.stationprogram

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stationprogram.databinding.AddProgramFormBinding

class AddProgramActivity: AppCompatActivity()  {
    private lateinit var binding : AddProgramFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddProgramFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addProgramButton.setOnClickListener { addProgram() }

        binding.back.setOnClickListener { onBackPressed() }
    }

    private fun addProgram(){

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}