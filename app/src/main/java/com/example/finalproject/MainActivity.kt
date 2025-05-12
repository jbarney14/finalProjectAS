package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import java.util.TimerTask

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

    var bulletOriginX = 0f
    var bulletOriginY = 0f

    var radius = 37f

    var fired = false

    var onLevel2 = false

    var playerHasMoved = false

    var playerGameWon : Boolean = false
    var playerGameOver : Boolean = false
    var isTransitioning : Boolean = false

    var score : Int = 0
    var lives : Int = 3
    var chronometerStartTime : Long = 0


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
                playerHasMoved = true
                xReq = e.x
                yReq = e.y - 136
                return true
            }
        })

        val prefs = getSharedPreferences("finalProjectPrefs", MODE_PRIVATE)
        prefs.edit().putLong("startTime", SystemClock.elapsedRealtime()).apply()
        chronometerStartTime = SystemClock.elapsedRealtime()

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
        isTransitioning = false
    }

    fun updateModel() {
        if (!playerGameOver) {
            if (onLevel2 == false) {
                var game = gameView.getGame()
                game.update()
            } else {
                val game2 = gameView2.getGame()
                game2.update()
            }
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

    var gameTimer: Timer? = null
    fun setTimer() {
        gameTimer?.cancel()
        gameTimer = Timer()
        val task = GameTimerTask( this )
        gameTimer!!.schedule( task, 0, 1)
    }

    fun getPlayerX() : Float {
        return xPos
    }

    fun getPlayerY() : Float {
        return yPos
    }

    fun restartGame() {
        gameTimer?.cancel()
        xPos = 100f
        yPos = 123f
        xReq = 100f
        yReq = 123f
        isTransitioning = false
        onLevel2 = false
        playerGameWon = false
        playerGameOver = false

        gameView = GameView(this, 4, this)
        setContentView(gameView)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    setTimer()
                }
            }
        }, 200)
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