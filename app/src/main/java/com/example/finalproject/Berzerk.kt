package com.example.finalproject

import android.graphics.Point
import android.util.Log
import kotlin.math.sqrt

class Berzerk {

    private var main : MainActivity

    constructor(mainActivity : MainActivity) {
        main = mainActivity
    }

    fun movePlayer(newX: Float, newY: Float) {
        val currentX = main.getPlayerX()
        val currentY = main.getPlayerY()

        val targetX = newX
        val targetY = newY

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



}