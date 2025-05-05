package com.example.finalproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.View
import java.util.Timer
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.random.Random

class GameView : View {

    private var enemies = 0
    val spawners = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var spawnOnce = 1
    var enemyRects = CopyOnWriteArrayList<RectF>()
    val wallRects = CopyOnWriteArrayList<RectF>()
    val enemyPositions = mutableListOf<Array<Int>>()
    val radius = 37f

    private var paint: Paint
    private lateinit var gameBitmap: Bitmap
    private lateinit var gameCanvas: Canvas
    private var berzerk: Berzerk
    private var main : MainActivity


    constructor(context : Context, enemies : Int, mainActivity: MainActivity) : super(context) {
        this.enemies = enemies
        paint = Paint()
        main = mainActivity

      val  playerRect = RectF(main.getPlayerX() - radius, main.getPlayerY() - radius,
            main.getPlayerX() + radius, main.getPlayerY() + radius )

        berzerk = Berzerk(mainActivity, playerRect, enemyRects, wallRects)


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

        paint.strokeWidth = 1f
        paint.color = Color.RED
        canvas.drawLine(0f, 25f, resources.displayMetrics.widthPixels.toFloat(),
            25f, paint)

        //outer lines
        paint.strokeWidth = 50f
        paint.color = Color.LTGRAY

        canvas.drawLine(0f, 0f, resources.displayMetrics.widthPixels.toFloat(),
            0f, paint)
        gameCanvas.drawLine(0f, 0f, resources.displayMetrics.widthPixels.toFloat(),
            0f, paint)
        wallRects.add(RectF(0f, -25f, resources.displayMetrics.widthPixels.toFloat(), 25f))

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

        canvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 4f, resources.displayMetrics.heightPixels.toFloat() / 8f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
            resources.displayMetrics.heightPixels.toFloat() / 8f , paint)
        gameCanvas.drawLine(resources.displayMetrics.widthPixels.toFloat() / 4f, resources.displayMetrics.heightPixels.toFloat() / 8f, resources.displayMetrics.widthPixels.toFloat() / (4/3f),
            resources.displayMetrics.heightPixels.toFloat() / 8f , paint)

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

        //player
        paint.color = Color.GREEN
        canvas.drawCircle(main.getPlayerX(), main.getPlayerY() - 134f, radius, paint)
        gameCanvas.drawCircle(main.getPlayerX(), main.getPlayerY() - 134f, radius, paint)

        //enemies - random spawn
        if (spawnOnce == 1) {
            spawnOnce++
            for (i in 0 until enemies) {
                val coords = spawnEnemies()
                enemyPositions.add(coords)
            }
        }

        for (coords in enemyPositions) {
            var enemyRect = RectF()
            paint.color = Color.RED
            canvas.drawCircle(coords[0].toFloat(), coords[1].toFloat(), radius, paint)

        }


    }

    fun spawnEnemies(): Array<Int> {
            var enemyCoordinates = arrayOf(0, 0)
            spawnOnce = 2
            val randomNumber = spawners.random() //spawner place on map

            when (randomNumber) {
                1 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
                }

                2 -> {
                    enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
                }

                3 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
                }

                4 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / 2
                }

                5 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
                }

                6 -> {
                    enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
                }

                7 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
                }

                8 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
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

    fun getGame() : Berzerk {
        return berzerk
    }



}