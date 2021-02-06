package com.example.asteroidsKotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Button
import kotlin.random.Random

class Ship(context: Context) : FlyingObject() {

    /** basic content **/
    val shipSpeed = 10f

    init {
        name = "Ship"
        wrap = true
        radius = 200
        rotation = 90f
        point = Game.getCenterPoint()
        point.x -= 150
        point.y -= 150

        launch(shipSpeed)
    }

    /** event handling **/
    fun accelerate() { accelerate(shipSpeed) }


    /** bitmap frames **/
    // src=https://www.clipartkey.com/view/iRhiwi_8-bit-spaceship-png-8-bit-spaceship-sprites/
    val frames = arrayListOf<Bitmap>()

    init {
        frames.add(createBitmap(context, R.drawable.ship_1))
        frames.add(createBitmap(context, R.drawable.ship_2))
        frames.add(createBitmap(context, R.drawable.ship_3))
        frames.add(createBitmap(context, R.drawable.ship_4))
        frames.add(createBitmap(context, R.drawable.ship_5))
        frames.add(createBitmap(context, R.drawable.ship_6))
        frames.add(createBitmap(context, R.drawable.ship_7))
        frames.add(createBitmap(context, R.drawable.ship_8))
        frames.add(createBitmap(context, R.drawable.ship_9))
        frames.add(createBitmap(context, R.drawable.ship_10))
        frames.add(createBitmap(context, R.drawable.ship_11))
        frames.add(createBitmap(context, R.drawable.ship_12))

        for (i in 0..11) frames[i] = resizeBitmap(frames[i], radius)
    }

    fun getFrame(): Bitmap {
        for (i in 0..11)
            if (rotation.toInt() in i*30..(i+1)*30)
                return frames[i] // return frame at correct rotation

        return frames[0] // default: up
    }

    private fun createBitmap(context: Context, drawable:Int) =
            BitmapFactory.decodeResource(context.resources, drawable)
    private fun resizeBitmap(bitmap: Bitmap, size: Int) =
            Bitmap.createScaledBitmap(bitmap, size, size, false)


}