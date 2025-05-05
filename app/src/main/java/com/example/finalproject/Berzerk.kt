package com.example.finalproject

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.RectF
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.math.sqrt

class Berzerk {

    private var main : MainActivity
    private var playerRect : RectF
    private var enemyRects: CopyOnWriteArrayList<RectF>
    private var wallRects: CopyOnWriteArrayList<RectF>

    constructor(mainActivity : MainActivity, playerRect : RectF,
                enemyRects : CopyOnWriteArrayList<RectF>, wallRects : CopyOnWriteArrayList<RectF>) {
        main = mainActivity
        this.playerRect = playerRect
        this.enemyRects = enemyRects
        this.wallRects = wallRects
    }

    fun update() {
        movePlayer()
        checkCollisions()

    }

    fun movePlayer() {
        val currentX = main.getPlayerX()
        val currentY = main.getPlayerY()

        val targetX = main.xReq
        val targetY = main.yReq

        // Movement speed
        val moveSpeed = 40f

        // Calculate the delta (distance) to move
        val deltaX = targetX - currentX
        val deltaY = targetY - currentY

        // Calculate the distance to the target point
        val distance = Math.sqrt((deltaX * deltaX + deltaY * deltaY).toDouble()).toFloat()

        // If the distance is greater than moveSpeed, keep moving the player
        if (distance > moveSpeed) {
            val moveX = (deltaX / distance) * moveSpeed
            val moveY = (deltaY / distance) * moveSpeed

            // Update player position
            main.setPlayerX(currentX + moveX)
            main.setPlayerY(currentY + moveY)

            // Redraw the view
            main.updateView()
        } else {
            // If we're close enough, set the player's position to the target
            main.setPlayerX(targetX)
            main.setPlayerY(targetY)
            main.updateView()
        }
    }

    fun checkCollisions() {
        for(rect in wallRects) {
            Log.w("MainActivity", "wall: " + rect.toShortString())
            Log.w("MainActivity", "player: " + playerRect.toShortString())
            if(RectF.intersects(playerRect, rect)) {
                Log.w("MainActivity", "hit!")
            }
        }
    }



}