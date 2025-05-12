package com.example.finalproject

import android.content.Intent
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
    private lateinit var gameView2: GameView2
    private lateinit var gestureDetector: GestureDetector
    private var clickCheck = false
    var xPos = 100f
    var yPos = 123f

    var xReq = 100f
    var yReq = 123f

    var bulletx = 100f
    var bullety = 123f

    var bulletxReq = 100f
    var bulletyReq = 123f

    var radius = 37f

    var fired = false

    var onLevel2 = false

    companion object {
        var instance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                fired = true
                Log.w("MainActivity", fired.toString())
                bulletx = xPos
                bullety = yPos
                bulletxReq = e.x
                bulletyReq = e.y - 136
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
        gameView2 = GameView2(this, 4, this)

        setContentView(gameView)
        //setContentView(gameView2)

        //Test Game Over Screen()
        //showGameOverScreen()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun startGameView2() {
        setContentView(gameView2)
    }

    fun updateModel() {
        if (onLevel2 == false) {
            var game = gameView.getGame()
            game.update()
        } else {
            val game2 = gameView2.getGame()
            game2.update()
        }
    }

    fun updateView() {
        if (onLevel2 == false) {
            gameView.postInvalidate()
        } else {
            gameView2.postInvalidate()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setTimer()
    }

    fun setTimer() {
        val timer = Timer()
        val task = GameTimerTask( this )
        timer.schedule( task, 0, 16)
    }

    fun getPlayerX() : Float {
        return xPos
    }

    fun getPlayerY() : Float {
        return yPos
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
        val intent = Intent(this, GameOverActivity::class.java)
        startActivity(intent)
    }

    /*
    fun modifyData() {
        var intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
    */
}