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
    //private lateinit var gameView2: GameView
    private lateinit var gestureDetector: GestureDetector
    private var clickCheck = false
    var xPos = 100f
    var yPos = 123f

    var xReq = 100f
    var yReq = 123f

    var radius = 37f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // fireBullet()
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                xReq = e.x
                yReq = e.y - 136
                return true
            }
        })

        gameView = GameView(this, 4, this)
        radius = gameView.radius
        //gameView2 = GameView(this, 4, this)

        setContentView(gameView)
        //setContentView(gameView2)

        //Test Game Over Screen()
        //showGameOverScreen()
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

    fun restartGame() {
        xPos = 100f
        yPos = 259f
        xReq = 100f
        yReq = 259f

        gameView = GameView(this, 4, this)
        setContentView(gameView)
        setTimer()
    }

    fun showGameOverScreen() {
        val gameOverView = GameView3(this, this)
        setContentView(gameOverView)
    }

}