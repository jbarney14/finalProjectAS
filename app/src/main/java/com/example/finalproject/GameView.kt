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
    val enemyRects = mutableListOf<Pair<RectF, Int>>()
    val enemyPositions = mutableListOf<Array<Int>>()
    val radius = 37f


    private var paint: Paint
    private lateinit var gameBitmap: Bitmap
    private lateinit var gameCanvas: Canvas
    private lateinit var berzerk: Berzerk
    private var main : MainActivity


    constructor(context : Context, enemies : Int, mainActivity: MainActivity) : super(context) {
        this.enemies = enemies
        paint = Paint()
        main = mainActivity
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gameBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        gameCanvas = Canvas(gameBitmap)

        berzerk = Berzerk(main, enemyRects, gameBitmap)
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        gameCanvas.drawColor(Color.BLACK)

        paint.strokeWidth = 1f
        paint.color = Color.RED
        canvas.drawLine(0f, 25f, width.toFloat(),
            25f, paint)

        //outer lines
        paint.strokeWidth = 50f
        paint.color = Color.LTGRAY

        //top
        gameCanvas.drawLine(0f, 0f, width.toFloat(),
            0f, paint)

        //left
        gameCanvas.drawLine(0f, 0f, 0f,
            height.toFloat(), paint)

        //bottom
        gameCanvas.drawLine(0f, height.toFloat(),
            width.toFloat(), height.toFloat(), paint)

        //right up
        gameCanvas.drawLine(width.toFloat(), 0f, width.toFloat(),
            height.toFloat() / 2f - 200f, paint)

        //right down
        gameCanvas.drawLine(width.toFloat(), height.toFloat(), width.toFloat(),
            height.toFloat() / 2f + 200f, paint)

        // Goal
        paint.strokeWidth = 50f
        paint.color = Color.BLUE

        gameCanvas.drawLine(width.toFloat(), height.toFloat() / 2f - 200f, width.toFloat(),
            height.toFloat() / 2f + 200f, paint)

        //inner maze
        paint.strokeWidth = 25f
        paint.color = Color.LTGRAY

        gameCanvas.drawLine(width.toFloat() / 4f, height.toFloat() / 8f, width.toFloat() / (4/3f),
            height.toFloat() / 8f , paint)

        gameCanvas.drawLine(width.toFloat() / 3f, height.toFloat() / 4f, width.toFloat() / (4/3f),
            height.toFloat() / 4f , paint)

        gameCanvas.drawLine(width.toFloat() / (4/3f) - 12f, height.toFloat() / 4f, width.toFloat() / (4/3f) - 12f,
            height.toFloat() / (3/2f) , paint)

        gameCanvas.drawLine(width.toFloat() / 3f, height.toFloat() / (6/5f), width.toFloat() / (4/3f),
            height.toFloat() / (6/5f), paint)

        gameCanvas.drawLine(width.toFloat() / 3f + 12f, height.toFloat() / (6/5f), width.toFloat() / 3f + 12f,
            height.toFloat() / (6/5f) - 925f, paint)

        //player
        if(!getGame().printColis()) {
            paint.color = Color.GREEN
            gameCanvas.drawCircle(main.getPlayerX(), main.getPlayerY(), radius, paint)
        } else {
            paint.color = Color.WHITE
            gameCanvas.drawCircle(main.getPlayerX(), main.getPlayerY(), radius, paint)
        }

        //player bullet
        if(main.fired) {
                paint.color = Color.GREEN
                gameCanvas.drawCircle(main.bulletx, main.bullety, radius / 2, paint)
            }


        //enemy bullet
        for (i in berzerk.enemyBulletActive.indices) {
            if (berzerk.enemyBulletActive[i]) {
                paint.color = Color.RED
                gameCanvas.drawCircle(berzerk.enemyBulletX[i], berzerk.enemyBulletY[i], radius / 2, paint)
            }
        }


        //enemies - random spawn
        if (spawnOnce == 1) {
            spawnOnce++
            for (i in 0 until enemies) {
                val coords = spawnEnemies()
                enemyPositions.add(coords)
            }
        }

        for (coords in enemyPositions) {
            var enemyRect = RectF(coords[0] - radius, coords[1] - radius,
                coords[0] + radius, coords[1] + radius)
            if (enemyRects.none { it.first == enemyRect }) {
                enemyRects.add(Pair(enemyRect, coords[2]))
            }
            paint.color = Color.RED
            if(!berzerk.hitList.contains(coords[2])) {
                gameCanvas.drawCircle(coords[0].toFloat(), coords[1].toFloat(), radius, paint)
            }

        }

        canvas.drawBitmap(gameBitmap, 0f, 0f, null)
    }

    fun spawnEnemies(): Array<Int> {
            var enemyCoordinates = arrayOf(0, 0, 0)
            spawnOnce = 2
            val randomNumber = spawners.random() //spawner place on map

            when (randomNumber) {
                1 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
                    enemyCoordinates[2] = 1
                }

                2 -> {
                    enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
                    enemyCoordinates[2] = 2
                }

                3 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / (8 / 1.5).toInt()
                    enemyCoordinates[2] = 3
                }

                4 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / 2
                    enemyCoordinates[2] = 4
                }

                5 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / 3.5)).toInt()
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
                    enemyCoordinates[2] = 5
                }

                6 -> {
                    enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
                    enemyCoordinates[2] = 6
                }

                7 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (6.0 / 5.5)).toInt()
                    enemyCoordinates[2] = 7
                }

                8 -> {
                    enemyCoordinates[0] =
                        (resources.displayMetrics.widthPixels / (4.0 / .5)).toInt()
                    enemyCoordinates[1] = resources.displayMetrics.heightPixels / 2
                    enemyCoordinates[2] = 8
                }

                9 -> {
                    enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (4.0 / 1.5) - 100).toInt()
                    enemyCoordinates[2] = 9
                }

                10 -> {
                    enemyCoordinates[0] = resources.displayMetrics.widthPixels / 2
                    enemyCoordinates[1] =
                        (resources.displayMetrics.heightPixels / (4.0 / 3.5) - 300).toInt()
                    enemyCoordinates[2] = 10
                }
            }


            spawners.remove(randomNumber)
        return enemyCoordinates

    }

    fun getGame() : Berzerk {
        return berzerk
    }



}