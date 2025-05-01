package com.example.finalproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import kotlin.random.Random

class GameView : View {

    private var enemies = 0

    private lateinit var paint: Paint
    private lateinit var gameBitmap: Bitmap
    private lateinit var gameCanvas: Canvas
   // private lateinit var berzerk: Berzerk

    constructor(context : Context, enemies : Int) : super(context) {
        this.enemies = enemies
        paint = Paint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gameBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        gameCanvas = Canvas(gameBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.BLACK)
        gameCanvas.drawColor(Color.BLACK)

        paint.strokeWidth = 50f
        paint.color = Color.LTGRAY

        canvas.drawLine(0f, 0f, resources.displayMetrics.widthPixels.toFloat(),
            0f, paint)
        gameCanvas.drawLine(0f, 0f, resources.displayMetrics.widthPixels.toFloat(),
            0f, paint)
        canvas.drawLine(0f, 0f, 0f,
            resources.displayMetrics.heightPixels.toFloat(), paint)
        gameCanvas.drawLine(0f, 0f, 0f,
            resources.displayMetrics.heightPixels.toFloat(), paint)
        canvas.drawLine(0f, resources.displayMetrics.heightPixels.toFloat(),
            resources.displayMetrics.widthPixels.toFloat(), resources.displayMetrics.heightPixels.toFloat(), paint)
        gameCanvas.drawLine(0f, resources.displayMetrics.heightPixels.toFloat(),
            resources.displayMetrics.widthPixels.toFloat(), resources.displayMetrics.heightPixels.toFloat(), paint)
        canvas.drawLine(resources.displayMetrics.widthPixels.toFloat(), 0f, resources.displayMetrics.widthPixels.toFloat(),
            resources.displayMetrics.heightPixels.toFloat() / 2f - 200f, paint)
        gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat(), 0f, resources.displayMetrics.widthPixels.toFloat(),
            resources.displayMetrics.heightPixels.toFloat() / 2f - 200f, paint)
        canvas.drawLine(resources.displayMetrics.widthPixels.toFloat(), resources.displayMetrics.heightPixels.toFloat(), resources.displayMetrics.widthPixels.toFloat(),
            resources.displayMetrics.heightPixels.toFloat() / 2f + 200f, paint)
        gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat(), resources.displayMetrics.heightPixels.toFloat(), resources.displayMetrics.widthPixels.toFloat(),
            resources.displayMetrics.heightPixels.toFloat() / 2f + 200f, paint)

        //player
        paint.color = Color.GREEN
        canvas.drawCircle(200f, 200f, 50f, paint)
        gameCanvas.drawCircle(200f, 200f, 50f, paint)

        //enemies - random spawn
        for(i in 0 .. enemies - 1) {
            paint.color = Color.RED
            var enemyCoordinates : Array<Int> = checkEnemyBounds()
            canvas.drawCircle(enemyCoordinates[0].toFloat(), enemyCoordinates[1].toFloat(), 50f, paint)
        }

    }

    fun checkEnemyBounds(): Array<Int> {
        val enemyCoordinates = arrayOf(0, 0)
        var attempts = 0
        val maxAttempts = 10000000
        val perimeterSamples = 10000  // Number of points to sample around the perimeter

        while (attempts < maxAttempts) {
            val enemyCenterX = Random.nextInt(100, resources.displayMetrics.widthPixels - 100)
            val enemyCenterY = Random.nextInt(100, resources.displayMetrics.heightPixels - 100)

            // Check if the perimeter touches anything other than the background
            var overlap = false

            for (i in 0 until perimeterSamples) {
                val angle = 2 * Math.PI * i / perimeterSamples
                val x = (enemyCenterX + 50 * Math.cos(angle)).toInt()
                val y = (enemyCenterY + 50 * Math.sin(angle)).toInt()

                if (gameBitmap.getPixel(x, y) != Color.BLACK) {
                    overlap = true
                    break
                }
            }

            if (overlap) {
                attempts++
                continue
            }
            // No overlap found, set the enemy's coordinates
            enemyCoordinates[0] = enemyCenterX
            enemyCoordinates[1] = enemyCenterY
            break
        }

        return enemyCoordinates
    }




}