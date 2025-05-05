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
import android.view.MotionEvent

class GameView3 : View{

    private lateinit var paint: Paint
    private lateinit var main: MainActivity

    constructor(context: Context, mainActivity: MainActivity) : super(context) {
        paint = Paint()
        main = mainActivity
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.BLACK)

        paint.color = Color.RED
        paint.textSize = 100f
        canvas.drawText("GAME OVER", width / 4f, height / 2f, paint)

        paint.color = Color.WHITE
        paint.textSize = 70f
        canvas.drawText("Tap to Replay", width / 3f, height / 2f + 150f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            main.restartGame()
            return true
        }
        return false
    }




}