package com.example.stationprogram

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stationprogram.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityMainBinding
    private lateinit var database : DatabaseReference
    private val programsList = arrayListOf<Program>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addProgramButton.setOnClickListener { startActivity(Intent(this, AddProgramActivity::class.java)) }


        binding.bottomNavigation.setOnItemSelectedListener{
            if(it.itemId == R.id.station_one ) {
                readAllData(1)
                programsList.clear()
                true
            }
            else {
                readAllData(2)
                programsList.clear()
                true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun readAllData(station : Int) {
        database = FirebaseDatabase.getInstance().getReference("Programs")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        if (dataSnapshot.child("station").value.toString().toInt() == station) {

                            val name = dataSnapshot.child("name").value.toString()
                            val station = dataSnapshot.child("station").value.toString().toInt()
                            val duration = dataSnapshot.child("duration").value.toString().toInt()
                            val startingTime = LocalTime.parse(dataSnapshot.child("startingTime").value.toString()).toString()
                            val endingTime = LocalTime.parse(dataSnapshot.child("endingTime").value.toString()).toString()

                            programsList.add(Program(
                                name,
                                station,
                                duration,
                                startingTime,
                                endingTime
                            ))
                        }
                    }
                }
                val adapter = ProgramsAdapter(programsList)
                binding.stationList.layoutManager = LinearLayoutManager(baseContext)
                binding.stationList.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}