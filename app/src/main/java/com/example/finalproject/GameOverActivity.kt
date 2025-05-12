package com.example.finalproject

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import android.content.Intent
import android.widget.Button

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)


        var adView = AdView(this)
        var adSize = AdSize(AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT)
        adView.setAdSize(adSize)
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        var adLayout = findViewById<LinearLayout>(R.id.ad_view)
        adLayout.addView(adView)

        var builder = AdRequest.Builder()
        builder.addKeyword("games")
        builder.addKeyword("fun")
        var request = builder.build()
        adView.loadAd(request)


        val tapText = findViewById<TextView>(R.id.tapToReplay)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val submitButton = findViewById<Button>(R.id.submitButton)
        tapText.setOnClickListener {
            MainActivity.instance?.restartGame()
            finish()
        }

        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)

        submitButton.setOnClickListener {
            val rating = ratingBar.rating.toInt()
            val recipients = arrayOf("testmyapp@gmail.com")

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.setType("text/plain")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Game Feedback - Player rating: $rating stars")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I just rated the game $rating stars.\n\nHere is my detailed feedback:\n")

            startActivity(Intent.createChooser(emailIntent, "Send Feedback Email"))

            Snackbar.make(rootLayout, "Thank you! Rating submitted.", Snackbar.LENGTH_SHORT).show()
            //ratingBar.isEnabled = false
            //submitButton.isEnabled = false
        }


    }
}