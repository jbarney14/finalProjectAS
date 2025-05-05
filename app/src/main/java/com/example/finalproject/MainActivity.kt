package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private lateinit var gestureDetector: GestureDetector
    private var clickCheck = false
    private var xPos = 100f
    private var yPos = 259f

    var xReq = 100f
    var yReq = 259f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // fireBullet()
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                clickCheck = true
                xReq = e.x
                yReq = e.y
                return true
            }
        })

        gameView = GameView(this, 4, this)

        setContentView(gameView)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun updateModel() {
        var game = gameView.getGame()
        game.update()
    }

    fun updateView() {
        gameView.postInvalidate()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setTimer()
    }

    fun setTimer() {
        val timer = Timer()
        val task = GameTimerTask( this )
        timer.schedule( task, 0, 100 )
    }

    fun getPlayerX() : Float {
        return xPos
    }

    fun getPlayerY() : Float {
        return yPos
    }

    fun setPlayerX(x : Float) {
        xPos = x
    }

    fun setPlayerY(y : Float) {
        yPos = y
    }

}
