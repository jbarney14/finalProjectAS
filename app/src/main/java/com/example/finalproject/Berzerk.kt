package com.example.finalproject

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.RectF
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Berzerk(
    private val main: MainActivity,
    private val enemyRects: List<RectF>,
    private val gameBitmap: Bitmap,
    private val width : Int,
    private val height : Int
) {

    var delayInMillis = 0L
    var colis = false

    fun update() {
        movePlayer()
        playerCollisions()
        printColis()
    }

    fun movePlayer() {
        val dx = main.xReq - main.xPos
        val dy = main.yReq - main.yPos
        val dist = Math.hypot(dx.toDouble(), dy.toDouble())

        val delayInSeconds = dist / 400f
        delayInMillis = (delayInSeconds * 1000).toLong()


        val speed = 40f
        if (dist > speed) {
            val angle = Math.atan2(dy.toDouble(), dx.toDouble())
            main.xPos += (Math.cos(angle) * speed).toFloat()
            main.yPos += (Math.sin(angle) * speed).toFloat()
        } else {
            main.xPos = main.xReq
            main.yPos = main.yReq
        }
    }

    fun playerCollisions(): Boolean {
        //Log.w("MainActivity", enemyRects.toString())

        val x = main.xPos.toInt()
        val y = main.yPos.toInt()
        val r = main.radius.toInt()

        val edgePoints = listOf(
            Pair(x - r, y),     // Left
            Pair(x + r, y),     // Right
            Pair(x, y - r),     // Top
            Pair(x, y + r),     // Bottom
            Pair(x - r, y - r), // Top-left
            Pair(x + r, y - r), // Top-right
            Pair(x - r, y + r), // Bottom-left
            Pair(x + r, y + r)  // Bottom-right
        )

        for ((px, py) in edgePoints) {
            // Bounds check
            if (px in 0 until gameBitmap.width && py in 0 until gameBitmap.height) {
                try {
                    val color = gameBitmap.getPixel(px, py)
                    when (color) {
                        Color.LTGRAY -> {
                            Log.d("MainActivity", "Collision with wall at ($px, $py)")
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(delayInMillis)
                                main.xPos = 100f
                                main.xReq = 100f
                                main.yPos = 123f
                                main.yReq = 123f
                                colis = true
                            }
                        }

                        Color.RED -> {
                            Log.d("MainActivity", "Collision with enemy at ($px, $py)")
                            // Handle enemy hit logic
                            return true
                        }

                        Color.BLUE -> {
                            main.modifyData()

                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error reading pixel: ${e.message}")
                }
            }
        }
        return false
    }

    fun printColis(): Boolean {
        if(colis) {
            colis = false
            return true
        } else {
            return false
        }
    }




    }


