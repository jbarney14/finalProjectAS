package com.example.finalproject

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val scores = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        listView = findViewById(R.id.leaderboardList)
        //val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores)
        //listView.adapter = adapter

        //val db = FirebaseDatabase.getInstance().getReference("Leaderboard")
        //db.addValueEventListener(object : ValueEventListener {
        //    override fun onDataChange(snapshot: DataSnapshot) {
        //        scores.clear()
        //        for (entry in snapshot.children) {
        //            val name = entry.child("name").value.toString()
        //            val score = entry.child("score").value.toString()
        //            val lives = entry.child("lives").value.toString()
        //            scores.add("$name → Score: $score, Lives: $lives")
        //        }
        //        adapter.notifyDataSetChanged()
        //    }

        //    override fun onCancelled(error: DatabaseError) {}
        //})
    }

}