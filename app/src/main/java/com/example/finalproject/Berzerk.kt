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
    private val enemyRects: List<Pair<RectF, Int>>,
    private val gameBitmap: Bitmap,

    /*
    // Flag to determine what level player is on, so reaching the goal leads to level 2 or the finish screen
    private var level2 : Boolean = false

     */
) {

    var delayInMillis = 0L

    var colis = false

    var bulletonScreen = true

    var enemyShoot = false

    var angle = 0.0

    var hitList = arrayListOf(0)

    val enemyCooldown = mutableMapOf<Int, Int>()  // positionID -> cooldown frames

    val enemyBulletX = FloatArray(10) { 0f }
    val enemyBulletY = FloatArray(10) { 0f }
    val enemyBulletXReq = FloatArray(10) { 0f }
    val enemyBulletYReq = FloatArray(10) { 0f }
    val enemyBulletActive = BooleanArray(10) { false }

    init {
        for ((rect, positionID) in enemyRects) {
            enemyCooldown[positionID] = 0
        }
    }


    fun update() {
        movePlayer()
        playerCollisions()
        playerBulletCollisions()
        enemyBulletCollisions()
        printColis()
        playerShoot()
        enemyShoot()
        moveEnemyBullet()
    }

    fun movePlayer() {

        // Log.w("MainActivity", main.xPos.toString() + ", " +  main.yPos.toString())

        val dx = main.xReq - main.xPos
        val dy = main.yReq - main.yPos
        val dist = Math.hypot(dx.toDouble(), dy.toDouble())

        val delayInSeconds = dist / 400f
        delayInMillis = (delayInSeconds * 1000).toLong()


        val speed = 5f
        if (dist > speed) {
            angle = Math.atan2(dy.toDouble(), dx.toDouble())
            main.xPos += (Math.cos(angle) * speed).toFloat()
            main.yPos += (Math.sin(angle) * speed).toFloat()
        } else {
            main.xPos = main.xReq
            main.yPos = main.yReq
        }
    }

    fun playerCollisions() {

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
                            main.xPos = 100f
                            main.bulletx = 100f
                            main.xReq = 100f
                            main.bulletxReq = 100f
                            main.yPos = 123f
                            main.bullety = 123f
                            main.yReq = 123f
                            main.bulletyReq = 123f
                            colis = true

                        }

                        Color.RED -> {
                            Log.d("MainActivity", "Collision with enemy at ($px, $py)")
                            main.xPos = 100f
                            main.bulletx = 100f
                            main.xReq = 100f
                            main.bulletxReq = 100f
                            main.yPos = 123f
                            main.bullety = 123f
                            main.yReq = 123f
                            main.bulletyReq = 123f
                        }

                        Color.BLUE -> {
                            // Going from level 1 to level 2
                            if (main.onLevel2 == false) {
                                Log.w("Berzerk.kt", "Hit the blue end of game")
                                main.runOnUiThread {
                                    main.startGameView2()
                                }

                                main.xPos = 100f
                                main.bulletx = 100f
                                main.xReq = 100f
                                main.bulletxReq = 100f
                                main.yPos = 123f
                                main.bullety = 123f
                                main.yReq = 123f
                                main.bulletyReq = 123f

                                colis = true
                                main.onLevel2 = true

                                // Return value?
                            } else {
                                // Go to game won screen
                            }


                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error reading pixel: ${e.message}")
                }
            }
        }
    }

    fun playerBulletCollisions() {


        val x = main.bulletx.toInt()
        val y = main.bullety.toInt()
        val r = main.radius.toInt() / 2

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
                            Log.d("MainActivity", "Bullet collision with wall at ($px, $py)")
                            main.bulletx = main.xPos
                            main.bullety = main.yPos
                            main.bulletxReq = main.xPos
                            main.bulletyReq = main.yPos
                            main.fired = false
                            bulletonScreen = false
                        }

                        Color.RED -> {
                            for ((rect, positionID) in enemyRects) {
                                if (rect.contains(px.toFloat(), py.toFloat())) {
                                    hitList.add(positionID)
                                    main.bulletx = main.xPos
                                    main.bullety = main.yPos
                                    main.bulletxReq = main.xPos
                                    main.bulletyReq = main.yPos
                                    main.fired = false

                                }

                            }
                        }


                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error reading pixel: ${e.message}")
                }
            }
        }
    }

    fun enemyBulletCollisions() {
        for (i in enemyBulletX.indices) {
            if (enemyBulletActive[i]) {
                val bulletX = enemyBulletX[i].toInt()
                val bulletY = enemyBulletY[i].toInt()

                // Check if the bullet is within the bounds of the game world
                if (bulletX in 0 until gameBitmap.width && bulletY in 0 until gameBitmap.height) {
                    try {
                        // Get the color of the pixel where the bullet is
                        val color = gameBitmap.getPixel(bulletX, bulletY)

                        when (color) {
                            Color.LTGRAY -> {
                                enemyBulletActive[i] = false
                                // Handle collision with light gray color (e.g., background)
                                // Perform necessary actions like stopping the bullet, or destroying an object
                            }
                            Color.GREEN -> {
                                // Deactivate all enemy bullets
                                for (i in enemyBulletActive.indices) {
                                    enemyBulletActive[i] = false  // Stop all active enemy bullets
                                }

                                // Reset the player position and other properties
                                main.xPos = 100f
                                main.yPos = 123f
                                main.xReq = 100f
                                main.yReq = 123f

                                // Flag that the player hasn't moved yet
                                main.playerHasMoved = false

                                // Optional: Reset any other game state here (score, health, etc.)
                            }
                        }
                    } catch (e: Exception) {
                        // Handle any potential errors gracefully, e.g., out-of-bounds
                        e.printStackTrace()
                    }
                }
            }
        }
    }



    fun printColis(): Boolean {
        if (colis) {
            colis = false
            return true
        } else {
            return false
        }
    }

    fun playerShoot() {
        if (main.fired) {

            val dx = main.bulletxReq - main.xPos
            val dy = main.bulletyReq - main.yPos
            val dist = Math.hypot(dx.toDouble(), dy.toDouble())

            val speed = 5f
            if (dist > speed) {
                val angle = Math.atan2(dy.toDouble(), dx.toDouble())
                main.bulletx += (Math.cos(angle) * speed).toFloat()
                main.bullety += (Math.sin(angle) * speed).toFloat()
            } else {
                main.bulletx = main.bulletxReq
                main.bullety = main.bulletyReq
            }
        }
    }

    fun enemyShoot() {
        if (main.playerHasMoved) {
            for ((rect, positionID) in enemyRects) {

                if (positionID in hitList) continue

                // Update cooldown timer
                val currentCooldown = enemyCooldown.getOrDefault(positionID, 0)
                if (currentCooldown > 0) {
                    enemyCooldown[positionID] = currentCooldown - 1
                    continue  // Skip shooting this enemy
                }

                val Xcenter = rect.right - main.radius
                val Ycenter = rect.bottom - main.radius

                if (main.xPos > rect.left && main.xPos < rect.right) {
                    // Fire a bullet
                    for (i in enemyBulletActive.indices) {
                        if (!enemyBulletActive[i]) {
                            enemyBulletX[i] = Xcenter
                            enemyBulletY[i] = Ycenter
                            enemyBulletXReq[i] = main.xPos
                            enemyBulletYReq[i] = main.yPos
                            enemyBulletActive[i] = true

                            enemyCooldown[positionID] = 300  // Cooldown for 1 second at 60 FPS
                            break
                        }
                    }
                } else if (main.yPos > rect.top && main.yPos < rect.bottom) {
                    for (i in enemyBulletActive.indices) {
                        if (!enemyBulletActive[i]) {
                            enemyBulletX[i] = Xcenter
                            enemyBulletY[i] = Ycenter
                            enemyBulletXReq[i] = main.xPos
                            enemyBulletYReq[i] = main.yPos
                            enemyBulletActive[i] = true

                            enemyCooldown[positionID] = 300  // Cooldown for 1 second at 60 FPS
                            break
                        }
                    }
                }
            }
        }
    }


        fun moveEnemyBullet() {
            val speed = 3f
            for (i in enemyBulletActive.indices) {
                if (enemyBulletActive[i]) {
                    val dx = enemyBulletXReq[i] - enemyBulletX[i]
                    val dy = enemyBulletYReq[i] - enemyBulletY[i]
                    val dist = Math.hypot(dx.toDouble(), dy.toDouble())

                    if (dist > speed) {
                        val angle = Math.atan2(dy.toDouble(), dx.toDouble())
                        enemyBulletX[i] += (Math.cos(angle) * speed).toFloat()
                        enemyBulletY[i] += (Math.sin(angle) * speed).toFloat()
                    } else {
                        // Bullet reached destination, deactivate
                        enemyBulletActive[i] = false
                    }
                }
            }
        }

    }



