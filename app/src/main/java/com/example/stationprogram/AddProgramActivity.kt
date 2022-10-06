package com.example.stationprogram

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import androidx.appcompat.app.AppCompatActivity
import com.example.stationprogram.databinding.AddProgramFormBinding
import java.time.LocalTime

class AddProgramActivity: AppCompatActivity()  {


    private lateinit var binding : AddProgramFormBinding
    private lateinit var database : DatabaseReference
    private lateinit var program : Program
    private val programsList = arrayListOf<Program>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddProgramFormBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = FirebaseDatabase.getInstance().getReference("Programs")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val name = dataSnapshot.child("name").value.toString()
                        val station = dataSnapshot.child("station").value.toString().toInt()
                        val duration = dataSnapshot.child("duration").value.toString().toInt()
                        val startingTime = LocalTime.parse(dataSnapshot.child("startingTime").value.toString()).toString()
                        val endingTime = LocalTime.parse(dataSnapshot.child("endingTime").value.toString()).toString()

                        programsList.add(Program(name, station, duration, startingTime,endingTime ))
                    }

                    binding.addProgramButton.setOnClickListener {

                        programsList.forEach {

                            if (programsList.isNotEmpty()&& binding.previousEt.text.toString().isNotEmpty() &&
                                it.name ==  binding.previousEt.text.toString()){

                                val newStartTime =  LocalTime.parse(it.endingTime).plusMinutes(2)
                                val duration = binding.durationEt.text.toString().toInt()
                                val endingTime = newStartTime.plusMinutes(duration.toLong())

                                program = Program(binding.nameEt.text.toString(),
                                    it.station,
                                    duration,
                                    newStartTime.toString(),
                                    endingTime.toString())

                            } else{
                                // per te shtuar programet e para te dites
//                                val startingTime = LocalTime.parse("06:00")
//
//                                program = Program(binding.nameEt.text.toString(),
//                                    binding.stationEt.text.toString().toInt(),
//                                    binding.durationEt.text.toString().toInt(),
//                                    startingTime.toString(),
//                                    startingTime.plusMinutes(binding.durationEt.text.toString().toLong()).toString())

                                return@forEach
                            }
                        }

                        database.child(binding.nameEt.text.toString()).setValue(program).addOnSuccessListener {
                            binding.nameEt.text?.clear()
                            binding.previousEt.text?.clear()
                            binding.durationEt.text?.clear()
                            binding.stationEt.text?.clear()
                        }
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding.back.setOnClickListener { onBackPressed() }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}