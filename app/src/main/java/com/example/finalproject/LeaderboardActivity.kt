package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
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
        val backButton = findViewById<Button>(R.id.backToGameOverButton)
        backButton.setOnClickListener {
            val intent = Intent(this, GameOverActivity::class.java)
            startActivity(intent)
            finish()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, scores)
        listView.adapter = adapter
        Log.d("FirebaseDebug", "Inside LeaderBoardCreate")

        val db = FirebaseDatabase.getInstance().getReference("Leaderboard")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scores.clear()
                for (player in snapshot.children) {
                    val name = player.key ?: "Unknown"
                    val highScore =  player.child("highScore").getValue(Int::class.java) ?: 0
                    val time = player.child("timeForHighScore").getValue(Long::class.java) ?: 0
                    val rating = player.child("rating").getValue(Int::class.java) ?: 0
                    val timeStr = formatTime(time)
                    scores.add("$name → Score: $highScore   Time: $timeStr   Rating: $rating★")
                }
                adapter.notifyDataSetChanged()
                Log.d("FirebaseDebug", "Entries found: ${snapshot.childrenCount}")
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val secs = seconds % 60
        return String.format("%d:%02d", minutes, secs)
    }

}