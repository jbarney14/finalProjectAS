package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {


    private lateinit var nameEditText: EditText
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.google.firebase.FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_start)

        nameEditText = findViewById(R.id.nameEditText)
        startButton = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            val playerName = nameEditText.text.toString()
            if (playerName.isNotEmpty()) {
                val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
                prefs.edit().putString("playerName", playerName).apply()



                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


}