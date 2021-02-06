package com.example.asteroidsKotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.random.Random

class Asteroid(context: Context) : FlyingObject() {

    companion object {
        val initAmount = 5
    }

    val asteroidSpeed = 10f

    // src=http://pixelartmaker.com/art/b02b88d8461a4fb
    var bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.asteroid)

    init {
        wrap = true

        point = Game.getRandomPointOnScreen()

        rotation = 360f * Random.nextFloat()
        launch(asteroidSpeed)

        radius  = 200
        bitmap  = Bitmap.createScaledBitmap(bitmap,
            radius, radius+10, false)
    }
}