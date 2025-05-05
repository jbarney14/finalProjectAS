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


class GameView2 : View {
    private var enemies = 0
    val spawners = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

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

        //outer lines
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

        //inner maze
        paint.strokeWidth = 25f

        val width = resources.displayMetrics.widthPixels.toFloat()
        val height = resources.displayMetrics.heightPixels.toFloat()

        // Top
        canvas.drawLine(width / 5f, height / 8f, 4*width / 5,
            height / 8f , paint)
        gameCanvas.drawLine(width / 5f, height / 8f, (4*width) / 5f,
            height / 8f , paint)

        // Top Left
        canvas.drawLine(width / 5f, height / 8.3f, width / 5f,
            height / 3.7f, paint)
        gameCanvas.drawLine(width / 5f, height / 8.3f, width / 5f,
            height / 3.7f , paint)

        // 2nd Horizontal
        canvas.drawLine(width / 5f, height / 3.7f, 3*width / 5,
            height / 3.7f , paint)
        gameCanvas.drawLine(width / 5f, height / 3.7f, 3*width / 5f,
            height / 3.7f , paint)

        // 3rd Horizontal
        canvas.drawLine(width / 5f, height / 2.4f, 2*width / 5,
            height / 2.4f , paint)
        gameCanvas.drawLine(width / 5f, height / 2.4f, 2*width / 5f,
            height / 2.4f , paint)


        // Mid Left
        canvas.drawLine(width / 5f, height / 2.4f, width / 5f,
            height / 1.4f , paint)
        gameCanvas.drawLine(width / 5f, height / 2.4f, width / 5f,
            height / 1.4f , paint)

        // 2/5 Bottom
        canvas.drawLine((2*width) / 5f, height / 2.4f, (2*width) / 5f,
            height / 1.2f  , paint)
        gameCanvas.drawLine((2*width) / 5f, height / 2.4f, (2*width) / 5f,
            height / 1.2f  , paint)

        // 3/5 Bottom
        canvas.drawLine((2*width) / 5f, height / 2.25f, (2*width) / 5f,
            height / 1.2f  , paint)
        gameCanvas.drawLine((2*width) / 5f, height / 2.25f, (2*width) / 5f,
            height / 1.2f  , paint)

        // 3/5 Middle
        canvas.drawLine((3*width) / 5f, height / 3.7f, (3*width) / 5f,
            height / 1.4f  , paint)
        gameCanvas.drawLine((3*width) / 5f, height / 3.7f, (3*width) / 5f,
            height / 1.4f  , paint)

        // 4/5 Bottom
        canvas.drawLine((4*width) / 5f, height / 2.4f, (4*width) / 5f,
            height / 1.2f  , paint)
        gameCanvas.drawLine((4*width) / 5f, height / 2.4f, (4*width) / 5f,
            height / 1.2f  , paint)

        // 4/5 Top
        canvas.drawLine((4*width) / 5f, height / 3.7f, (4*width) / 5f,
            height / 1.2f  , paint)
        gameCanvas.drawLine((4*width) / 5f, height / 3.7f, (4*width) / 5f,
            height / 1.2f  , paint)

        // Bottom
        canvas.drawLine(width / 5f, height / 1.2f, (4*width) / 5f,
            height / 1.2f , paint)
        gameCanvas.drawLine(width / 5f, height / 1.2f, (4*width) / 5f,
            height / 1.2f , paint)

        /*
                canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 4f, resources.displayMetrics.heightPixels.toFloat() / 8f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / 8f , paint)
                gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 4f, resources.displayMetrics.heightPixels.toFloat() / 8f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / 8f , paint)

                canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 4f, resources.displayMetrics.heightPixels.toFloat() / 4f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / 4f , paint)
                gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f, resources.displayMetrics.heightPixels.toFloat() / 4f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / 4f , paint)

                canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f, resources.displayMetrics.heightPixels.toFloat() / 4f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / 4f , paint)
                gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f, resources.displayMetrics.heightPixels.toFloat() / 4f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / 4f , paint)

                canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / (4/3f) - 12f, resources.displayMetrics.heightPixels.toFloat() / 4f, resources.displayMetrics.widthPixels.toFloat() / (4/3f) - 12f,
                    resources.displayMetrics.heightPixels.toFloat() / (3/2f) , paint)
                gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / (4/3f) - 12f, resources.displayMetrics.heightPixels.toFloat() / 4f, resources.displayMetrics.widthPixels.toFloat() / (4/3f) - 12f,
                    resources.displayMetrics.heightPixels.toFloat() / (3/2f) , paint)

                canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f, resources.displayMetrics.heightPixels.toFloat() / (6/5f), resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / (6/5f) , paint)
                gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f, resources.displayMetrics.heightPixels.toFloat() / (6/5f), resources.displayMetrics.widthPixels.toFloat() / (4/3f),
                    resources.displayMetrics.heightPixels.toFloat() / (6/5f), paint)

                canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f + 12f, resources.displayMetrics.heightPixels.toFloat() / (6/5f), resources.displayMetrics.widthPixels.toFloat() / 3f + 12f,
                    resources.displayMetrics.heightPixels.toFloat() / (6/5f) - 925f, paint)
                gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 3f + 12f, resources.displayMetrics.heightPixels.toFloat() / (6/5f), resources.displayMetrics.widthPixels.toFloat() / 3f + 12f,
                    resources.displayMetrics.heightPixels.toFloat() / (6/5f) - 925f, paint)
        */



        //player
        paint.color = Color.GREEN
        canvas.drawCircle(100f, 125f, 37f, paint)
        gameCanvas.drawCircle(100f, 100f, 37f, paint)

        //enemies - random spawn
        for(i in 0 .. enemies - 1) {
            paint.color = Color.RED
            var enemyCoordinates : Array<Int> = checkEnemyBounds()
            canvas.drawCircle(enemyCoordinates[0].toFloat(), enemyCoordinates[1].toFloat(), 37f, paint)
        }

    }

    fun checkEnemyBounds(): Array<Int> {
        var enemyCoordinates = arrayOf(0, 0)
        val randomNumber = spawners.random() //spawner place on map

        when (randomNumber) {
            1 -> {
                enemyCoordinates[0] = (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
            }

            2 -> {
                enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
            }

            3 -> {
                enemyCoordinates[0] = (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
            }

            4 -> {
                enemyCoordinates[0] = (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                enemyCoordinates[1] = resources.displayMetrics.heightPixels / 2
            }

            5 -> {
                enemyCoordinates[0] = (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                enemyCoordinates[1] = (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
            }

            6 -> {
                enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                enemyCoordinates[1] = (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
            }

            7 -> {
                enemyCoordinates[0] = (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                enemyCoordinates[1] = (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
            }

            8 -> {
                enemyCoordinates[0] = (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                enemyCoordinates[1] = resources.displayMetrics.heightPixels / 2
            }

            9 -> {
                enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                enemyCoordinates[1] =
                    (resources.displayMetrics.heightPixels / (4.0 / 1.5) - 100).toInt()
            }

            10 -> {
                enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                enemyCoordinates[1] =
                    (resources.displayMetrics.heightPixels / (4.0 / 3.5) - 300).toInt()
            }
        }

        spawners.remove(randomNumber)
        return enemyCoordinates
    }
}